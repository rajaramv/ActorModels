package com.rajaram.actor;

import com.rajaram.actor.beans.Message;
import com.rajaram.actor.core.Actor;
import com.rajaram.actor.core.ActorSystem;
import com.rajaram.exception.MessageNotDeliveredException;

public class ShippingActor extends Actor {
	
	public  ShippingActor() {
	}

	@Override
	public void work(Message msg) { 
		System.out.println("I'm shipping Actor: " + msg.getData());
		ActorSystem actorSys = ActorSystem.getInstance();
		Message newMsg = new Message();
		newMsg.setTargetActor(ActorSystem.getInstance().instanceOf(QueryActor.class));
		newMsg.setData("Query for the order " +msg.getData());
		try {
			actorSys.send(newMsg);
		} catch (MessageNotDeliveredException e) {
			System.out.println("Message could not be delivered " + newMsg.getData());
		}
		
	}

}
