package com.vipshop.microscope.collector.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.collector.alerter.MessageAlerter;
import com.vipshop.microscope.collector.server.CollectorServer;
import com.vipshop.microscope.common.trace.Span;

/**
 * A thread worker alert span. 
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class MessageAlertWorker implements Runnable {
	
	private final MessageAlerter alerter = new MessageAlerter();
	private final LinkedBlockingQueue<Span> queue;
	
	public MessageAlertWorker(LinkedBlockingQueue<Span> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		while (true) {
			Span span = queue.poll();

			if (span != null) {
				alerter.alert(span);
			} else {
				try {
					TimeUnit.MILLISECONDS.sleep(CollectorServer.SLEEP_TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
