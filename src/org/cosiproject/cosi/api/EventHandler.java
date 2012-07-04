/* EventHandler.java is part of Cosi
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
package org.cosiproject.cosi.api;

import java.util.ArrayList;

import org.apache.log4j.Logger;



public abstract class EventHandler {
	private static EventHandler instance;
	private static ArrayList<Event> listEvent = new ArrayList<Event>();
	private static Logger logger = Logger.getLogger(EventHandler.class);
	
	public static void callEvent(Class clazz, int ev) {
		logger.debug("Class: " + clazz + " eventName: " + ev);
		for(Event e : listEvent) { 
			if(clazz.getCanonicalName().equals(e.getEventClass().getCanonicalName())) {
				logger.debug("Found EventClass: " + e.getClass());
				if(ev == e.getEventNum()) {
					logger.debug("\tFound Event: " + ev);
					e.onEvent();
				}
			}
		}
		
		
	}
	public static void callEvent(Class clazz, int ev, int state) {
		logger.debug("Class: " + clazz + " eventName: " + ev + " state: " + state);
		for(Event e : listEvent) { 
			if(clazz.getCanonicalName().equals(e.getEventClass().getCanonicalName())) {
				logger.debug("\tFound EventClass: " + e.getClass());
				if(ev == e.getEventNum()) {
					logger.debug("\t\tFound Event: " + ev+"(" + state + ")");
					e.onEvent(state);
				}
			}
		}
		
	}
	public static void registerEventListener(Event event) {
		// TODO Auto-generated method stub
		logger.debug("Registering Event " + event);
		listEvent.add(event);
	}
	

}
