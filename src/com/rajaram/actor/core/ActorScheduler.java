package com.rajaram.actor.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import com.rajaram.actor.beans.Message;

public class ActorScheduler {
	
	
	ExecutorService threadPool = null;
	public ActorScheduler(ExecutorService threadPool) {
		this.threadPool = threadPool;
	}
	
	public void scheduleActor(Actor actor, Message msg) {
		actor.add(msg);
		if(!actor.isRunning())
			threadPool.execute(actor);
		
	}
	
	public boolean isShutDown() {
		return threadPool.isShutdown();
	}
	
	public void shutDown() throws InterruptedException {
		threadPool.awaitTermination(1, TimeUnit.SECONDS);
		 threadPool.shutdown();
	}

}
