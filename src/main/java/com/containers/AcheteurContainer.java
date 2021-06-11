package com.containers;

import com.agents.Acheteur;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AcheteurContainer extends Application{
	

	
	private Acheteur monAgent;
	ObservableList<String>oMessages=FXCollections.observableArrayList();
	TextField lMessages=null;
	
	public static void main(String[] args) {
		launch(AcheteurContainer.class);

	}
	public void startContainer(){
		Runtime r=Runtime.instance();
		Profile p=new ProfileImpl(false);
		p.setParameter(Profile.MAIN_HOST, "localhost");
		AgentContainer c=r.createAgentContainer(p);
		try {
			AgentController ac=c.createNewAgent("Acheteur", "com.agents.Acheteur", new Object[]{this});
			ac.start();
			
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Acheteur getMonAgent() {
		return monAgent;
	}

	public void setMonAgent(Acheteur monAgent) {
		this.monAgent = monAgent;
	}

	@Override
	public void start(Stage ps) throws Exception {
		startContainer();
		ps.setTitle("Acheteur");
		BorderPane root=new BorderPane();
		
		HBox h1=new HBox();
		h1.setPadding(new Insets(10));
		h1.setSpacing(10);
		Label l1=new Label("Produit: ");
		TextField t1=new TextField();
		Button b1=new Button("acheter le produit");
		
		b1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String id=t1.getText();
				GuiEvent guiEvent=new GuiEvent(this, 1);
				guiEvent.addParameter(id);
				getMonAgent().onGuiEvent(guiEvent);
			}
		});
		
		GridPane g1=new GridPane();
		g1.add(l1, 0, 0);
		g1.add(t1, 1, 0);
		g1.add(b1, 0, 1);
		h1.getChildren().add(g1);
		
		VBox v=new VBox();
		GridPane g=new GridPane();
		Label lab3=new Label("Messages recus: ");
		lMessages=new TextField("");
		lMessages.setDisable(true);
		
		lMessages.setPrefWidth(1000);
		lMessages.setPrefHeight(200);
		lMessages.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		lMessages.setStyle("color:black;");
		g.add(lab3, 1, 0);
		g.add(lMessages, 1, 1);
		v.getChildren().add(g);
		v.setSpacing(20);
		v.setPadding(new Insets(20));
		root.setCenter(v);
		root.setTop(h1);
		Scene sc=new Scene(root);
		ps.setScene(sc);
		ps.show();
		
		
		
	}
	public void afficherMessage(GuiEvent g){
		
		String m=g.getParameter(0).toString();
		lMessages.setText(m); 
		
		
	}

}
