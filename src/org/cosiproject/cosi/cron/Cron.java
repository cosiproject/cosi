/* Cron.java is part of Cosi
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
package org.cosiproject.cosi.cron;

import java.util.Date;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class Cron extends Thread {
	private static Logger logger = Logger.getLogger(Cron.class);
	private ArrayList<CronJob> jobList = new ArrayList<CronJob>();
	private static Cron instance;
	
	
	public static Cron getInstance() {
		if(instance == null) 
			instance = new Cron();
		return instance;
	}
	
	
	private Cron() {
		this.start();
	}
	
	
	public void run() {
		logger.debug("Starting Cron Daemon");
		int minute, hour, day, month, week;
		Date date;
		while(true) {
			try {
				Thread.sleep((1000*60));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
			date = new Date();
			logger.debug("Executing Jobs... ");
			logger.debug(date.getMinutes()+"; " + date.getHours()+"; " + date.getDay()+"; " + date.getMonth()+"; " + 0);
			for(CronJob job : jobList) {
				job.execute(date.getMinutes(), date.getHours(), date.getDay(), date.getMonth(), 0);
			}
			
			
			
		}
	}
	
	public void addCronJob(CronJob job) {
		// some checks
		jobList.add(job);
	}
	
	public void removeCronJob(CronJob job) {
		// some checks
		jobList.remove(job);
	}
}
