/*
 * Copyright (c) 2010, Regents of the University of California
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *  * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *  * Neither the name of the University of California, Berkeley
 * nor the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package radlab.rain.workload.mailapp;

import radlab.rain.Generator;
import radlab.rain.IScoreboard;
import radlab.rain.Operation;
import radlab.rain.util.HttpTransport;
import radlab.rain.util.storage.KeyGenerator;

/**
 * The SampleOperation class contains common static methods for use by the
 * operations that inherit from this abstract class.
 */
public abstract class MailAppOperation extends Operation 
{
	// These references will be set by the Generator.
	protected HttpTransport _http;
	
	private String smtpHost ;
	private String popHost ;
	
	/**
	 * Returns the SampleGenerator that created this operation.
	 * 
	 * @return      The SampleGenerator that created this operation.
	 */
	public MailAppGenerator getGenerator()
	{
		return (MailAppGenerator) this._generator;
	}
	
	public MailAppOperation( boolean interactive, IScoreboard scoreboard )
	{
		super( interactive, scoreboard );
	}
	
	@Override
	public void prepare(Generator generator) 
	{
		this._generator = generator;
		MailAppGenerator mailAppGenerator = (MailAppGenerator) generator;
		
		this._http = mailAppGenerator.getHttpTransport();
		smtpHost = popHost = mailAppGenerator.getEndpointAddress();
	
	}
	
	@Override
	public void cleanup()
	{
		
	}
	
	public String getUser(KeyGenerator gen){
		return String.valueOf(gen.generateKey());
	}
	public String getSmtpHost(){
		return smtpHost;
	}
	public String getPopHost(){
		return popHost;
	}
	
	
	
}
