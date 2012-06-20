/* ArgumentHelp.java is part of Cosi
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
package org.cosiproject.cosi.arguments;

import org.apache.log4j.Logger;
import org.cosiproject.cosi.Cosi;
import org.cosiproject.toolkit.argumentor.ArgumentCallback;
import org.cosiproject.toolkit.argumentor.ArgumentEvent;
import org.cosiproject.toolkit.argumentor.ArgumentHandler;
import org.cosiproject.toolkit.argumentor.ArgumentParameter;
import org.cosiproject.toolkit.argumentor.ArgumentSet;
import org.cosiproject.toolkit.argumentor.ArgumentSwitch;
import org.cosiproject.toolkit.localizer.Localizer;

public class ArgumentHelp extends ArgumentCallback {

	private static Logger logger = Logger.getLogger(ArgumentHelp.class);
	private static Localizer localizer = Localizer.getLocalizer(ArgumentHelp.class);
	
	@Override
	public void process(ArgumentEvent e) {
		// TODO Auto-generated method stub
//		System.out.println(Cosi.getVersionString());
//		System.out.println(localizer.getKey("usage")+"\t"+localizer.getKey("usage1"));
//		System.out.println();
//		System.out.println(localizer.getKey("options"));
//		for(ArgumentSwitch option : ArgumentHandler.getRootSet().getArgumentSwitches()) {
//			String param = new String();
//			for(ArgumentParameter parameter : option.getParameter()) {
//				param += ", " + parameter.getPrefix().getPrefix()+parameter.getParameter();
//			}
//			param = param.substring(2);
//			System.out.println("\t" + param + "\t\t-\t" + option.getHelpText());
//			
//		}
//		System.out.println();
//		System.out.println(localizer.getKey("pluginsets"));
//		for(ArgumentSet set : ArgumentHandler.getRootSet().getChilds()) {
//			System.out.println("  " + set.getName());
//			for(ArgumentSwitch option : set.getArgumentSwitches()) {
//				String param = new String();
//				for(ArgumentParameter parameter : option.getParameter()) {
//					param += ", " + parameter.getPrefix().getPrefix()+parameter.getParameter();
//				}
//				param = param.substring(2);
//				System.out.println("\t" + param + "\t\t-\t" + option.getHelpText());
//			}
//		}
//		System.out.println();
//		System.out.println();
//		System.exit(0);
	}
	
	

}
