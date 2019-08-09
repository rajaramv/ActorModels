package com.rajaram.actor;

import com.rajaram.actor.beans.Message;
import com.rajaram.actor.core.Actor;

public class QueryActor extends Actor {

	@Override
	public void work(Message msg) {
		System.out.println("I'm Query Actor " + msg.getData());
		
	}

}
