package com.containers;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;

public class MainContainer {

	public static void main(String[] args) {
		Runtime r=Runtime.instance();
		Properties p=new ExtendedProperties();
		p.setProperty(Profile.GUI, "true");
		Profile pr=new ProfileImpl(p);
		AgentContainer m=r.createMainContainer(pr);
		try {
			m.start();
		} catch (ControllerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
