/* CosiAgent.java is part of Cosi
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
package org.cosiproject.cosi.agent;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.SearchControls;

import org.apache.log4j.Logger;

public class CosiAgent {

	private static Logger logger = Logger.getLogger(CosiAgent.class);
	private static Instrumentation instrumentation;
	
	
	public static Instrumentation getInstrumentation() {
		return instrumentation;
	}
	public static void setInstrumentation(Instrumentation instrumentation) {
		CosiAgent.instrumentation = instrumentation;
	}
	public static void main(String[] args) {
		
	}
	public static void premain(String agentArgs, final Instrumentation inst) {
		instrumentation = inst;
		logger.debug("premain");
	}
	
	public static List<Class> getAllInstancesOf(Class searchClass) {
		logger.debug("I'm hit!");
		ArrayList<Class> retVal = new ArrayList<Class>();
		for(Class claz : instrumentation.getAllLoadedClasses()) {
			logger.debug("-> " + claz.getCanonicalName());
			for(Class tmp : claz.getClasses()) {
				logger.debug("\t\t-->" + tmp.getCanonicalName() + " == " + searchClass);
				if(tmp.getCanonicalName().equals(searchClass.getCanonicalName())) {
					logger.debug("\t\t!! Found: " + tmp + " == " + searchClass);
				}
			}
		}
			
		return retVal;
	}
}
