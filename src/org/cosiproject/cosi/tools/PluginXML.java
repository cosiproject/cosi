/* PluginXML.java is part of Cosi
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
package org.cosiproject.cosi.tools;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.cosiproject.cosi.core.pluginloader.xml.Classpath;
import org.cosiproject.cosi.core.pluginloader.xml.Meta;
import org.cosiproject.cosi.core.pluginloader.xml.CosiPluginModule;
import org.cosiproject.cosi.core.pluginloader.xml.Plugin;
import org.cosiproject.toolkit.version.Version;

public abstract class PluginXML {

	private static Logger logger = Logger.getLogger(PluginXML.class);
	
	
	public static void main(String[] args) {
		CosiPluginModule foo = new CosiPluginModule();
		Meta info = new Meta();
		info.setAuthor("Konradowicz");
		info.setDescription("A small description");
		info.setEmail("Foo@bar.de");
		info.setName("Foobert");
		
		// TODO: change to Meta
		foo.setMeta(info);
		
		Plugin plugin = new Plugin();
		plugin.setEntryPoint("net.lother.cosix.plugin.addressbook.Main");
		List<Classpath> ffoo = new ArrayList<Classpath>();
		Classpath cpmc = new Classpath();
		cpmc.setSrc("/Users/lotherk/Documents/workspace/plugins/CosiX/AddressBook.cpmx/src/");
		ffoo.add(cpmc);
		plugin.setListClasspath(ffoo);
		foo.setPlugin(plugin);
		
		Version v = new Version("1.2.3");
		foo.setVersion(v);
		
		createPluginXml(foo, new File("test0r.xml"));
		
		
	}
	
	public static void createPluginXml(CosiPluginModule plugin, File output) {
		logger.debug("Creating plugin.xml for " + plugin);
		try {
			JAXBContext context = JAXBContext.newInstance(CosiPluginModule.class);
			Marshaller m = context.createMarshaller();
			m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
			m.marshal(plugin, new FileWriter(output));
			logger.debug("plugin.xml written to " + output);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);

		}
	}
}
