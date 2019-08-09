package com.rajaram.actor.client;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rajaram.actor.ShippingActor;
import com.rajaram.actor.beans.Message;
import com.rajaram.actor.core.Actor;
import com.rajaram.actor.core.ActorSystem;
import com.rajaram.exception.MessageNotDeliveredException;

public class ActorDriver {

	public static void main(String[] args) throws Exception{

		ActorSystem system = ActorSystem.getInstance();

		//Initiate one actor of Shipping Actor and queue 500 messages.
		Actor shippingActor = system.instanceOf(ShippingActor.class);
		ExecutorService pool = Executors.newFixedThreadPool(3);
		int i=0;
		for(; i < 3; i++) {
			scheduleMessages(system, shippingActor, pool, i);
		}
		Thread.sleep(1000);
		//Initiate another instance of the Shipping actor
		Actor shippingActor1 = system.instanceOf(ShippingActor.class);
		for(; i < 6; i++) {
			scheduleMessages(system, shippingActor1, pool, i);
		}
		//shippingActor.stop();
		//Stop the actor system which will shut down the thread pool used to schedule the actors
		system.stop();
		// Thread pools are non deamon threads, shut it down
		pool.shutdown();
	}

	/**
	 * 
	 * @param system
	 * @param shippingActor
	 * @param pool
	 * @param i
	 */
	private static void scheduleMessages(ActorSystem system, Actor shippingActor, ExecutorService pool, int i) {
		Message msg = new Message();
		msg.setTargetActor(shippingActor);
		msg.setData("Shipping order " + i);
		Runnable shippingMessage = new Runnable() {

			@Override
			public void run() {
					try {
						system.send(msg);
					} catch (MessageNotDeliveredException e) {
						System.out.println("Message could not be delivered " + msg.getData());
					}
				}
		};
		pool.submit(shippingMessage);
	}

}

