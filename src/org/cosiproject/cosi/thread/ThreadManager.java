/* ThreadManager.java is part of Cosi
 * created 18.11.2011 
 *
 *
 * Copyright (c) 2011, The Cosi Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *    * Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   * Neither the name of the <organization> nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  
 * 
 * @author lotherk
 *
 * 
 */
package org.cosiproject.cosi.thread;

import org.apache.log4j.Logger;

public class ThreadManager extends Thread {

	private static ThreadManager instance;
	private static Logger logger = Logger.getLogger(ThreadManager.class);
	
	public static ThreadManager getInstance() {
		// TODO Auto-generated method stub
		if(instance == null)
			instance = new ThreadManager();
		return instance;
	}
	
	private ThreadManager() {
		this.start();
	}
	
	public void run() {
		boolean run = false;
		
		while(run) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.debug("Checking Threads");
			ThreadGroup rootGroup = Thread.currentThread( ).getThreadGroup( );
			ThreadGroup parentGroup;
			while ( ( parentGroup = rootGroup.getParent() ) != null ) {
			    rootGroup = parentGroup;
			}
			Thread[] threads = new Thread[ rootGroup.activeCount() ];
			while ( rootGroup.enumerate( threads, true ) == threads.length ) {
			    threads = new Thread[ threads.length * 2 ];
			}

			for(int i = 0; i < threads.length; i++) {
				if(threads[i] != null) {
					logger.debug("Checking " + threads[i].getName()+ " (" + threads[i].getId()+") prio: " + threads[i].getPriority() + ", state:" + threads[i].getState() + ", group: " + threads[i].getThreadGroup().getName());					
				}
			}
			
		}
	}

}
