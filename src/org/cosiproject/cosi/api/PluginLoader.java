/* PluginLoader.java is part of Cosi
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.cosiproject.cosi.api.pluginloader.PluginPool;


public abstract class PluginLoader {
	private List<File> listPluginDirectory = new ArrayList<File>();
	private PluginPool<PluginContainer> pluginPool;
	private String suffix;
	private static Logger logger = Logger.getLogger(PluginLoader.class);

	
	public abstract void load();
	public abstract void preLoadPlugins();
	protected abstract void readPlugin(String path);
	public abstract void createPlugins();
	
	
	/**
	 * Return the directories that contain the plugins.
	 * @return
	 */
	public List<File> getPluginDirectoryList() {
		return listPluginDirectory;
	}
	
	/**
	 * Returns the suffix of the plugins this PluginLoader wants to load.
	 * @return
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * The PluginLoader will look for folders ending with the suffix given by this Method.
	 * e.g. "cpm" if the plugin ends with .cpm.
	 * @param suffix
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public PluginPool<PluginContainer> getPluginPool() {
		return pluginPool;
	}
	public void setPluginPool(PluginPool<PluginContainer> p) {
		pluginPool = p;
	}
	
	public void setPluginDirectoryList(List<File> list) {
		this.listPluginDirectory = list;
	}
	public void initPlugins() {
		// TODO Auto-generated method stub
		if(getPluginPool() == null) return;
		
		for(PluginContainer pluginContainer : getPluginPool()) {
			try {
				pluginContainer.getPlugin().init();
			} catch (NullPointerException e) {
				logger.debug(e);
			}
			catch (Exception e) {
				logger.warn(e);
			}
		}
	}
	
}
