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


import radlab.rain.IScoreboard;
import radlab.rain.util.storage.KeyGenerator;

/**
 * The Operation1 operation is a sample operation.
 */
public class SendMailOperation extends MailAppOperation 
{
	private MailAppGenerator gen;
	private KeyGenerator mailSizeGenerator;
	private KeyGenerator userKeyGenerator;
	
	public SendMailOperation( boolean interactive, IScoreboard scoreboard, MailAppGenerator gen ) 
	{
		super( interactive, scoreboard );
		this._operationName = "SendMailOperation";
		this._operationIndex = MailAppGenerator.SendMailOPERATION;
		this.gen=gen;
		mailSizeGenerator=gen.getMailSizeGenerator();
		userKeyGenerator=gen.getUserKeyGenerator();
	}
	
	@Override
	public void execute() throws Throwable
	{
		// TODO: Make a request.
		this.trace( this._operationName );
		int smtpOverhead = 400;
		int linebreakAfterChars = 75;
		// TODO: Fill me in.
		String host = this.getSmtpHost();
		String from = this.getUser(userKeyGenerator);
		String to = this.getUser(userKeyGenerator);
		int size=mailSizeGenerator.generateKey();
		//System.out.println("Size from generator: "+size);
		size=size-smtpOverhead;
		if(size<1) size=1;
		if(size>linebreakAfterChars){
			size=size-(size/linebreakAfterChars)*2;
		}
		//System.out.println("Adapted Size without Overhead: "+size);
		
		String mailContent = generateMailContent(from, to, size);
		//System.out.println("mailContent size :"+mailContent.length());
		
		String mailSubject = generateMailSubject(from, to);
	//	System.out.println("smtp request: host:"+host+" from:"+from+" to:"+to+" mailcontent: "+mailContent+" subject:"+mailSubject);
		gen.getSmtpAction().sendSmtpMail(host, from, to, mailContent, mailSubject);
		
		
		
		this.setFailed( false );
	}
	
	public String generateMailContent(String from, String to, int length){
		//TODO dynamic
		/*byte[] content = new byte[length];
		new Random().nextBytes(content);
		
		return new ByteArrayBuffer(content).toString();
		*/
		//return new StringByteIterator(size).toString();
		//return new BigInteger(size, gen.getRandomGenerator()).toString(32);
		//from http://www.java2s.com/Code/Java/Security/generatesanalphanumericstringbasedonspecifiedlength.htm
		
		
				      char[] values = {'a','b','c','d','e','f','g','h','i','j',
		               'k','l','m','n','o','p','q','r','s','t',
		               'u','v','w','x','y','z','0','1','2','3',
		               '4','5','6','7','8','9'};
		      String out = "";
		      for (int i=0;i<length;i++) {
		          int idx=gen.getRandomGenerator().nextInt(values.length);
		        out += values[idx];
		      }
		      return out;
		      
		    
		
	}
	public String generateMailSubject(String from, String to){
		return "Hi "+to;
	}
}
