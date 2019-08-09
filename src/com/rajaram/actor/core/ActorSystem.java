package com.rajaram.actor.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.rajaram.actor.beans.Message;
import com.rajaram.exception.MessageNotDeliveredException;

public class ActorSystem {

	/*Map<String, Actor> idActorMap = new HashMap<>();

	Map<String, Class> idActorClassMap = new HashMap<>();*/
	
	private volatile static  ActorSystem instance = null;
	private ActorScheduler scheduler = null;
	

	private  ActorSystem() {
		scheduler = new ActorScheduler(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
	}
	
	public static ActorSystem getInstance() {
		if(instance == null) {
			synchronized(ActorSystem.class) {
				if(instance == null) {
					instance = new ActorSystem();
				}
			}
		}
		return instance;
	}
	
	

	/*public void init() {
		ShippingActor actor = new ShippingActor(this);

		idActorMap.put(actor.getId(), actor);
		QueryActor qActor = new QueryActor(this);
		idActorMap.put(qActor.getId(), qActor);

		idActorClassMap.put("ShippingActor", ShippingActor.class);
		idActorClassMap.put("QueryActor", QueryActor.class);
	}*/

	public <T extends Actor> Actor   instanceOf(Class<T> actorClass)  
	{
		    try {
				return actorClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return null;
	}

	

	public void send(Message msg) throws MessageNotDeliveredException{
		
		Actor targetActor = msg.getTargetActor();
		if(scheduler.isShutDown() || targetActor.isStopped()) {
			throw new MessageNotDeliveredException();
		}
		
		scheduler.scheduleActor(targetActor,msg);

	}

	public void stop() throws InterruptedException {
		scheduler.shutDown();
	}

}
