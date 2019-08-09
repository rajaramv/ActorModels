package com.rajaram.actor.beans;

import com.rajaram.actor.core.Actor;

public class Message {
	
	 Object data;
	
	Actor targetActor;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Actor getTargetActor() {
		return targetActor;
	}

	public void setTargetActor(Actor targetActor) {
		this.targetActor = targetActor;
	}

}
