package com.rajaram.actor.core;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.rajaram.actor.beans.Message;

public abstract class Actor implements Runnable {

	private final BlockingQueue<Message> inbox = new LinkedBlockingQueue<>();
	private volatile boolean running = false;
	private volatile boolean stopped = false;

	public void add(Message msg) {
		inbox.offer(msg);
	}

	public synchronized boolean isRunning() {
		return running;
	}
	
	public synchronized boolean isStopped() {
		return stopped;
	}

	@Override
	public void run() {
		synchronized (this) {
			if(running) {
				return;
			}
			running = true;
		}
		while (!inbox.isEmpty()) {
			Message msg = inbox.poll();
			work(msg);
		}
		synchronized (this) {
			running = false;
		}
		
	}

	public abstract void work(Message msg);

	public void stop() {
		stopped = true;
	}

}
