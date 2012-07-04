/* PluginAPI.java is part of Cosi
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

import org.cosiproject.cosi.api.xml.Meta;

public abstract class PluginAPI {
	
	private String basePath;
	private Meta meta;
	private PluginContainer pluginContainer;

	public Meta getMeta() {
		return this.meta;
	}
	
	public void setMeta(Meta meta) {
		this.meta = meta;
	}
	

	protected PluginAPI() {

	}
	
//	/**
//	 * Returns the PluginContainer containing this Plugin.
//	 * @return
//	 */
//	public PluginContainer getPluginContainer() {
//		return pluginContainer;
//	}
//
//	/**
//	 * Sets the PluginContainer for this Plugin.
//	 * @param pluginContainer
//	 */
//	public void setPluginContainer(PluginContainer pluginContainer) {
//		this.pluginContainer = pluginContainer;
//	}
	
	
	
	/**
	 * Returns the BasePath of the Plugin. e.g. /home/foobar/COSI/Plugins/Foo.cpm
	 * @return
	 */
	public String getBasePath() {
		return basePath;
	}
	
	/**
	 * Set BasePath for this Plugin.
	 * @param basePath
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	/**
	 * This method is called directly while loading.
	 * This is useful to do things like register something to the EventHandler o.s.
	 */
	public abstract void onLoad();


	/**
	 * This method is called after all plugins are loaded.
	 * Basically, this is the point where your plugin starts its work.
	 */
	public abstract void init();

	public abstract void shutdown();	
	
	public abstract void preLoad();
	
	public abstract void install();
	
}
