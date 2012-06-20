/* CosiClassLoader.java is part of Cosi
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
package org.cosiproject.cosi;
/**
 * This might be used someday.
 */
import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class CosiClassLoader {

	private static Logger logger = Logger.getLogger(CosiClassLoader.class);

	
	public static void addToClassPath(ClassLoader classLoader, String path) throws MalformedURLException {
		if(classLoader instanceof URLClassLoader) {
			try {
				Method addUrlMethod = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] {URL.class});
				addUrlMethod.setAccessible(true);
				if(null != addUrlMethod) {
					try {
						logger.debug("Adding " + path + " to " + classLoader.toString());
						URL url = new File(path).toURI().toURL();
						
						addUrlMethod.invoke(classLoader, url);
					} catch (Exception e) {
						logger.error(e);
					}
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
	}
	
	/**
	 * Not working yet.
	 * @param classLoder
	 * @param path
	 */
	public static void removeFromClassPath(ClassLoader classLoder, String path) {
		
		

	}
	/**
	 * Not working yet.
	 * @param classLoder
	 * @param path
	 */
	public static void removeFromClassPath(ClassLoader classLoder, ArrayList<String> path) {

		
		
	}

}
