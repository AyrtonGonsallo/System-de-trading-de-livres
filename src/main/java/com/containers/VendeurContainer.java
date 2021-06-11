package com.containers;


import com.agents.Vendeur;
import jade.core.Profile;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class VendeurContainer extends Application{
	
//main lance l'app javafx qui appelle start() qui appelle startcontainer()
	
	private Vendeur monAgent;
	ObservableList<String>oMessages=FXCollections.observableArrayList();
	TextField oChoisis=null;
	AgentContainer c;
	public static void main(String[] args) {
		launch(VendeurContainer.class);

	}
	public void startContainer(){
		Runtime r=Runtime.instance();
		Profile p=new ProfileImpl(false);
		p.setParameter(Profile.MAIN_HOST, "localhost");
		c=r.createAgentContainer(p);
		try {
			AgentController ac=c.createNewAgent("Vendeur", "com.agents.Vendeur", new Object[]{this});
			ac.start();
		
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Vendeur getMonAgent() {
		return monAgent;
	}

	public void setMonAgent(Vendeur monAgent) {
		this.monAgent = monAgent;
	}

	@Override
	public void start(Stage ps) throws Exception {
		startContainer();
		ps.setTitle("Vendeur");
		BorderPane root=new BorderPane();
		
		
		
		VBox v=new VBox();
		GridPane g=new GridPane();
		Label lab2=new Label("Produits disponibles: ");
		
		oChoisis=new TextField("");
		oChoisis.setDisable(true);
		oChoisis.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		oChoisis.setStyle("color:black;");
		oChoisis.setPrefWidth(1000);
		oChoisis.setPrefHeight(200);
		Label lab3=new Label("Messages recus: ");
		ListView<String> lMessages=new ListView<String>(oMessages);
		g.add(lab2, 0, 0);
		g.add(oChoisis, 0, 1);
		g.add(lab3, 1, 0);
		g.add(lMessages, 1, 1);
		v.getChildren().add(g);
		v.setSpacing(20);
		v.setPadding(new Insets(20));
		
		
		root.setCenter(v);
		
		Scene sc=new Scene(root);
		ps.setScene(sc);
		ps.show();
		
		
		
		
	}
	public void afficherMessage(GuiEvent g){
		String m=g.getParameter(0).toString();
		oMessages.add(m);
		
	}
	public void afficherProduit(GuiEvent g){
		String m=g.getParameter(0).toString();
		oChoisis.setText(m);
		
	}

}
