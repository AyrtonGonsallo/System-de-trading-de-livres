package com.agents;


import java.util.List;

import com.containers.VendeurContainer;
import com.dao.ProduitDaoImpl;
import com.model.ProductParser;
import com.model.Produit;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ControllerException;

public class Vendeur extends GuiAgent {
	ProduitDaoImpl pdi=new ProduitDaoImpl();
	private List<Produit> liste=null;
	private VendeurContainer gui;
	public VendeurContainer getGui() {
		return gui;
	}
	public void setGui(VendeurContainer gui) {
		this.gui = gui;
	}
	private static final long serialVersionUID = 1L;
@Override
protected void setup() {
	setGui((VendeurContainer) getArguments()[0]);
	gui.setMonAgent(this);
	System.out.println("Initailisation de l'agent "+this.getAID().getLocalName());
	super.setup();
	ParallelBehaviour p=new ParallelBehaviour();
	addBehaviour(p);
	
	//s'enregistrer
		p.addSubBehaviour(new OneShotBehaviour() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public void action() {
				System.out.println("Inscription vendeur...");
				//moi
				DFAgentDescription df=new DFAgentDescription();
				df.setName(getAID());
				ServiceDescription sd=new ServiceDescription();
				sd.setType("Vente");
				sd.setName("Agent Vendeur "+getLocalName());
				df.addServices(sd);
				
				
				try {
					DFService.register(myAgent, df);
				} catch (FIPAException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				
			}
		});
		
	//repondre a un msg
	p.addSubBehaviour(new CyclicBehaviour() {
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			
			//le vendeur lui envoie la liste des produits
			MessageTemplate temp = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
			ACLMessage m = receive(temp);
			if(m!=null)
			{
				int id=Integer.parseInt(m.getContent());
				pdi.updateVENDU(id);
				System.out.println("produit "+id+" vendu");
				
				
			}
			else{
				block();
			}
			
		}
	});
	//demander la liste des acheteurs et envoyer les produits
	p.addSubBehaviour(new TickerBehaviour(this,20000) {
		
		private static final long serialVersionUID = 1L;

		@Override
		protected void onTick() {
			//on recupere la liste des acheteurs
			DFAgentDescription dfa=new DFAgentDescription();
			ServiceDescription sd=new ServiceDescription();
			sd.setType("Achat");
			dfa.addServices(sd);
			DFAgentDescription[]agents_desc=null;
			try {
				agents_desc=DFService.search(myAgent, dfa);
			} catch (FIPAException e) {
				
				e.printStackTrace();
			}
			if(agents_desc!=null){
				//si il y a des acheteurs on leur envoie la liste
				liste =pdi.get3List();
				ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
				message.setContent(ProductParser.toJson(liste));
				for(int i=0;i<agents_desc.length;i++){
					message.addReceiver(agents_desc[i].getName());
				}
				send(message);
				GuiEvent g=new GuiEvent(this, 1);
				String liste_affichée="";
				for(Produit p:liste){
					liste_affichée+="Produit: "+p.getName()+" de status: "+p.getStatus()+" de prix: "+p.getPrice()+" d'id: "+p.getId()+"\n";
				}
				g.addParameter(liste_affichée);
				getGui().afficherProduit(g);
			}
			else{
				//pas d'acheteurs on dort
				try {
					Thread.sleep(9000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	try {
		DFService.deregister(this);
	} catch (FIPAException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
@Override
public void onGuiEvent(GuiEvent guiEvent) {
}
}
