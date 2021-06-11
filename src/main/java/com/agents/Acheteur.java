package com.agents;


import java.util.List;

import com.containers.AcheteurContainer;
import com.model.ProductParser;
import com.model.Produit;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ControllerException;

public class Acheteur extends GuiAgent {
	List<Produit> productList;
	AID vendeur;
	private AID[] liste_vendeur;
	public AID[] getVendeur() {
		return liste_vendeur;
	}
	public void setVendeur(AID[] vendeur) {
		this.liste_vendeur = vendeur;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private AcheteurContainer gui;
	public AcheteurContainer getGui() {
		return gui;
	}
	public void setGui(AcheteurContainer gui) {
		this.gui = gui;
	}
	private static final long serialVersionUID = 1L;
@Override
protected void setup() {
	setGui((AcheteurContainer) getArguments()[0]);
	gui.setMonAgent(this);
	System.out.println("Initailisation de l'agent "+this.getAID().getLocalName());
	super.setup();
	ParallelBehaviour p=new ParallelBehaviour();
	addBehaviour(p);
	
	//s'enregistrer
	p.addSubBehaviour(new OneShotBehaviour() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			DFAgentDescription dfad=new DFAgentDescription();
			dfad.setName(getAID());
			ServiceDescription sd=new ServiceDescription();
			sd.setType("Achat");
			sd.setName("Agent Acheteur "+getLocalName());
			dfad.addServices(sd);
			try {
				DFService.register(myAgent, dfad);
			} catch (FIPAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	});
	
	//lire les messages recus
	p.addSubBehaviour(new CyclicBehaviour() {
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			//le vendeur lui envoie la liste des produits
			MessageTemplate temp = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
			ACLMessage m = receive(temp);
			if(m!=null)
			{
				String productsJson = m.getContent();
				productList = ProductParser.fromJson(productsJson);
				GuiEvent g=new GuiEvent(this, 1);
				String liste_affichée="";
				for(Produit p:productList){
					liste_affichée+="Produit: "+p.getName()+" de status: "+p.getStatus()+" de prix: "+p.getPrice()+" d'id: "+p.getId()+"\n";
				}
				g.addParameter(liste_affichée);
				getGui().afficherMessage(g);
				//System.out.println(liste_affichée);
				vendeur=m.getSender();
				
				
			}
			else{
				block();
			}
			
		}
	});
	
}
@Override
protected void beforeMove() {
	try {
		System.out.println("avant migration du container: "+this.getContainerController().getContainerName());
	} catch (ControllerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	super.beforeMove();
}
@Override
protected void afterMove() {
	try {
		System.out.println("apres migration vers le container: "+this.getContainerController().getContainerName());
	} catch (ControllerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	super.afterMove();
}
@Override
protected void takeDown() {
	System.out.println("Destruction de l'agent "+this.getAID().getLocalName());
	super.takeDown();
}
@Override
public void onGuiEvent(GuiEvent guiEvent) {
	//envoyer une alerte au vendeur qu'il changent les status
	ACLMessage a=new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
	a.setContent((String) guiEvent.getParameter(0));
	a.addReceiver(vendeur);
	send(a);
}
}
