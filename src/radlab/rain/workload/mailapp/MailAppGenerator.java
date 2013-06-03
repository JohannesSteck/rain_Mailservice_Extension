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

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import radlab.rain.Generator;
import radlab.rain.LoadProfile;
import radlab.rain.Operation;
import radlab.rain.ScenarioTrack;
import radlab.rain.util.HttpTransport;
import radlab.rain.util.KBLogNormalIntegerGenerator;
import radlab.rain.util.LogNormalIntegerGenerator;
import radlab.rain.util.storage.KeyGenerator;
import radlab.rain.util.storage.UniformKeyGenerator;
import radlab.rain.util.storage.YCSBzipfianGenerator;
import radlab.rain.util.storage.ZipfKeyGenerator;

/**
 * The SampleGenerator class generates operations for a single user thread
 * by producing the next operation to execute given the last operation. The
 * next operation is decided through the use of a load mix matrix. 
 */
public class MailAppGenerator extends Generator
{
	
	// Operation indices used in the mix matrix.
	public static final int SendMailOPERATION = 0;
	public static final int POPReceiveMailOPERATION = 1;
	//public static final int ReceiveMailLeaveOnServerOPERATION = 2;
	public static final int POPDeleteMailOPERATION = 2;
	public static final int POPEmptyOPERATION = 3;
	
	private java.util.Random _randomNumberGenerator;
	private HttpTransport _http;
	
	private PopAction pop;
	private SmtpAction smtp;
	
	
	private String MAILSIZEDISTRIBUTION_PARAM = "mailSizeDistribution";
	private String mailSizeDistribution="uniform";
	private String MAILSIZEMIN_PARAM = "minMailSize";
	private int minMailSize=64;
	private String MAILSIZEMAX_PARAM = "maxMailSize";
	private int maxMailSize=6400;
	private String MAILSIZEZIPF_A_PARAM = "zipfMailSize_a";
	private double mailSizeZipf_a=1.001;
	private String MAILSIZEZIPF_R_PARAM = "zipfMailSize_r";
	private double mailSizeZipf_r=3.456;
	private String MAILSIZELOGNROMAL_MEAN_PARAM="logNormalMailSize_mean";
	private double mailSizeLogNormal_mean=0.87;
	private String MAILSIZELOGNROMAL_SIGMA_PARAM="logNormalMailSize_sigma";
	private double mailSizeLogNormal_sigma=0.739;
	private KeyGenerator mailSizeGenerator;

	private String MAILCOUNTDISTRIBUTION_PARAM = "mailCountDistribution";
	private String mailCountDistribution="uniform";
	private String MAILCOUNTMIN_PARAM = "minMailCount";
	private int minMailCount=1;
	private String MAILCOUNTMAX_PARAM = "maxMailCount";
	private int maxMailCount=100;
	private String MAILCOUNTZIPF_A_PARAM = "zipfMailCount_a";
	private double mailCountZipf_a=1.001;
	private String MAILCOUNTZIPF_R_PARAM = "zipfMailCount_r";
	private double mailCountZipf_r=3.456;
	private String MAILCOUNTLOGNROMAL_MEAN_PARAM="logNormalMailCount_mean";
	private double mailCountLogNormal_mean=0.87;
	private String MAILCOUNTLOGNROMAL_SIGMA_PARAM="logNormalMailCount_sigma";
	private double mailCountLogNormal_sigma=0.739;
	
	private KeyGenerator mailCountGenerator;
	
	private String USERCHOOSER_PARAM = "userChooserDistribution";
	private String userChooserDistribution="uniform";
	private String MINUSERKEY_PARAM = "minUserKey";
	private int minUserKey=1;
	private String MAXUSERKEY_PARAM = "maxUserKey";
	private int maxUserKey=100;
	private String USERZIPF_A_PARAM = "zipfUser_a";
	private double zipfUser_a=1.001;
	private String USERZIPF_R_PARAM = "zipfUser_r";
	private double zipfUser_r=3.456;
	
	private KeyGenerator userKeyGenerator;
	
	private String RETRIEVECOUNTDISTRIBUTION_PARAM="retrieveCountDistribution";
	private String retrieveCountDistribution="uniform";
	private String RETRIEVEZIPF_A_PARAM = "zipfRetrieve_a";
	private double zipfRetrieve_a=1.001;
	private String RETRIEVEZIPF_R_PARAM = "zipfRetrieve_r";
	private double zipfRetrieve_r=3.456;
	
	private KeyGenerator retrieveCountGenerator;
	
	private String DELETECOUNTDISTRIBUTION_PARAM="deleteCountDistribution";
	private String deleteCountDistribution="ycsbZipfian";
	private String DELETEZIPF_A_PARAM = "zipfDelete_a";
	private double zipfDelete_a=1.001;
	private String DELETEZIPF_R_PARAM = "zipfDelete_r";
	private double zipfDelete_r=3.456;
	
	private KeyGenerator deleteCountGenerator;
	
	private String ENDPOINT_PARAM = "endpoint";
	public String endpointAddress="localhost";
	
	
	
	/**
	 * Initialize a <code>SampleGenerator</code> given a <code>ScenarioTrack</code>.
	 * 
	 * @param track     The track configuration with which to run this generator.
	 */
	public MailAppGenerator( ScenarioTrack track )
	{
		super( track );
		
		// TODO: Fill me in.
	}
	
	/**
	 * Initialize this generator.
	 */
	public void initialize()
	{
		this._randomNumberGenerator = new Random();
		this._http = new HttpTransport();
		this.pop=new PopAction(this);
		this.smtp=new SmtpAction();
		//mailSizeGenerator=new UniformKeyGenerator(minMailSize,maxMailSize,_randomNumberGenerator.nextLong());
		//mailCountGenerator=new UniformKeyGenerator(minMailCount,maxMailCount,_randomNumberGenerator.nextLong());
		//userKeyGenerator=new UniformKeyGenerator(minUserKey,maxUserKey,_randomNumberGenerator.nextLong());
		}
	
	@Override
	public void configure( JSONObject config ) throws JSONException
	{
		
		try{
			this._randomNumberGenerator = new java.util.Random();
		mailSizeDistribution=config.getString(MAILSIZEDISTRIBUTION_PARAM);		
		
		minMailSize=config.getInt(MAILSIZEMIN_PARAM);
		
		maxMailSize=config.getInt(MAILSIZEMAX_PARAM);
		System.out.println(mailSizeDistribution+" "+minMailSize+" "+maxMailSize);
		
		if(mailSizeDistribution.equals("lognormal")){
			mailSizeLogNormal_mean=config.getDouble(MAILSIZELOGNROMAL_MEAN_PARAM);
			mailSizeLogNormal_sigma=config.getDouble(MAILSIZELOGNROMAL_SIGMA_PARAM);
			//Parameters in KB, generated values in Bytes
			mailSizeGenerator=new KBLogNormalIntegerGenerator(Math.round(minMailSize/1024), Math.round(maxMailSize/1024), mailSizeLogNormal_mean, mailSizeLogNormal_sigma);
		}else if(mailSizeDistribution.equals("zipfian")){
			mailSizeZipf_a=config.getDouble(MAILSIZEZIPF_A_PARAM);
			mailSizeZipf_r=config.getDouble(MAILSIZEZIPF_R_PARAM);

			mailSizeGenerator=new ZipfKeyGenerator(mailSizeZipf_a,mailSizeZipf_r,minMailSize,maxMailSize,_randomNumberGenerator.nextLong());
		}else {
			mailSizeGenerator=
				new UniformKeyGenerator(
					minMailSize,
					maxMailSize,
					_randomNumberGenerator.nextLong()
					);
			
		}
		
		
		
		mailCountDistribution=config.getString(MAILCOUNTDISTRIBUTION_PARAM);
		minMailCount=config.getInt(MAILCOUNTMIN_PARAM);
		maxMailCount=config.getInt(MAILCOUNTMAX_PARAM);
		
		if(mailCountDistribution.equals("lognormal")){
			mailCountLogNormal_mean=config.getDouble(MAILCOUNTLOGNROMAL_MEAN_PARAM);
			mailCountLogNormal_sigma=config.getDouble(MAILCOUNTLOGNROMAL_SIGMA_PARAM);

			mailCountGenerator=new LogNormalIntegerGenerator(minMailCount,maxMailCount,mailCountLogNormal_mean,mailCountLogNormal_sigma);
		}else if(mailCountDistribution.equals("zipfian")){
			mailSizeZipf_a=config.getDouble(MAILCOUNTZIPF_A_PARAM);
			mailSizeZipf_r=config.getDouble(MAILCOUNTZIPF_R_PARAM);
			
			mailCountGenerator=new ZipfKeyGenerator(mailCountZipf_a,mailCountZipf_r,minMailCount,maxMailCount,_randomNumberGenerator.nextLong());
		}else {
			mailCountGenerator=new UniformKeyGenerator(minMailCount,maxMailCount,_randomNumberGenerator.nextLong());
		}
		 
		
		
		userChooserDistribution=config.getString(USERCHOOSER_PARAM);
		
		minUserKey=config.getInt(MINUSERKEY_PARAM);
		maxUserKey=config.getInt(MAXUSERKEY_PARAM);
		
		System.out.println(userChooserDistribution+" "+minUserKey+" "+maxUserKey);
		
		if(userChooserDistribution.equals("zipfian")){
			zipfUser_a=config.getDouble(USERZIPF_A_PARAM);
			zipfUser_r=config.getDouble(USERZIPF_R_PARAM);
			
			userKeyGenerator=new ZipfKeyGenerator(zipfUser_a,zipfUser_r,minUserKey,maxUserKey,_randomNumberGenerator.nextLong());
		}else {
			userKeyGenerator=new UniformKeyGenerator(minUserKey,maxUserKey,_randomNumberGenerator.nextLong());
		}
		
		
		deleteCountDistribution=config.getString(DELETECOUNTDISTRIBUTION_PARAM);
		retrieveCountDistribution=config.getString(RETRIEVECOUNTDISTRIBUTION_PARAM);
		
		if(deleteCountDistribution.equals("ycsbZipfian")){
			//use the ported Zipfian Generator from YCSB
			
			}else if(deleteCountDistribution.equals("zipfian")){
			zipfDelete_a=config.getDouble(DELETEZIPF_A_PARAM);
			zipfDelete_r=config.getDouble(DELETEZIPF_R_PARAM);
			
			}
		
		
		
		if(retrieveCountDistribution.equals("ycsbZipfian")){

			
			}else if(retrieveCountDistribution.equals("zipfian")){
				zipfRetrieve_a=config.getDouble(RETRIEVEZIPF_A_PARAM);
				zipfRetrieve_r=config.getDouble(RETRIEVEZIPF_R_PARAM);
				
				}
		
		
		endpointAddress=config.getString(ENDPOINT_PARAM);
		
		
		
		
		}catch (Exception e) {
			System.out.println("-------------------------------");
			e.printStackTrace();
			
		}
	}
	
	
	/**
	 * Returns the next <code>Operation</code> given the <code>lastOperation</code>
	 * according to the current mix matrix.
	 * 
	 * @param lastOperation     The last <code>Operation</code> that was executed.
	 */
	public Operation nextRequest( int lastOperation )
	{
		LoadProfile currentLoad = this.getTrack().getCurrentLoadProfile();
		int nextOperation = -1;
		
		if( lastOperation == -1 )
		{
			nextOperation = 0;
		}
		else
		{
			// Get the selection matrix
			double[][] selectionMix = this.getTrack().getMixMatrix(currentLoad.getMixName()).getSelectionMix();
			double rand = this._randomNumberGenerator.nextDouble();
			
			int j;
			for ( j = 0; j < selectionMix.length; j++ )
			{
				if ( rand <= selectionMix[lastOperation][j] )
				{
					break;
				}
			}
			nextOperation = j;
		}
		return getOperation( nextOperation );
	}
	
	/**
	 * Returns the current think time. The think time is duration between
	 * receiving the response of an operation and the execution of its
	 * succeeding operation during synchronous execution (i.e. closed loop).
	 */
	public long getThinkTime()
	{
		return _thinkTime;
	}
	
	/**
	 * Returns the current cycle time. The cycle time is duration between
	 * the execution of an operation and the execution of its succeeding
	 * operation during asynchronous execution (i.e. open loop).
	 */
	public long getCycleTime()
	{
		return _cycleTime;
	}
	
	/**
	 * Disposes of unnecessary objects at the conclusion of a benchmark run.
	 */
	public void dispose()
	{
		// TODO: Fill me in.
	}
	
	/**
	 * Returns the pre-existing HTTP transport.
	 * 
	 * @return          An HTTP transport.
	 */
	public HttpTransport getHttpTransport()
	{
		return this._http;
	}
	
	
	/**
	 * Creates a newly instantiated, prepared operation.
	 * 
	 * @param opIndex   The type of operation to instantiate.
	 * @return          A prepared operation.
	 */
	public Operation getOperation( int opIndex )
	{
		switch( opIndex )
		{
			case SendMailOPERATION: return this.createSendMailOperation();
			case POPReceiveMailOPERATION: return this.createReceiveMailOperation();
			case POPDeleteMailOPERATION: return this.createPOPDeleteMailOperation();
			case POPEmptyOPERATION: return this.createPOPEmptyMailOperation();
			
			//case ReceiveMailLeaveOnServerOPERATION: return this.createReceiveMailLeaveOnServerOperation();
			default:         return null;
		}
	}
	

	public SendMailOperation createSendMailOperation()
	{
		
		SendMailOperation op = new SendMailOperation( this.getTrack().getInteractive(), this.getScoreboard(), this );
		op.prepare( this );
		return op;
	}
	

	public POPReceiveMailOperation createReceiveMailOperation()
	{
		POPReceiveMailOperation op = new POPReceiveMailOperation( this.getTrack().getInteractive(), this.getScoreboard(), this );
		op.prepare( this );
		return op;
	}
	public POPDeleteMailOperation createPOPDeleteMailOperation()
	{
		POPDeleteMailOperation op = new POPDeleteMailOperation( this.getTrack().getInteractive(), this.getScoreboard(), this );
		op.prepare( this );
		return op;
	}
	public POPEmptyOperation createPOPEmptyMailOperation()
	{
		POPEmptyOperation op = new POPEmptyOperation(this.getTrack().getInteractive(), this.getScoreboard(), this);
		op.prepare( this );
		return op;
	}
	
	public SmtpAction getSmtpAction(){
		return this.smtp; 
	}
	public PopAction getPopAction(){
		return this.pop; 
	}
	
	public Random getRandomGenerator(){
		return this._randomNumberGenerator; 
	}

	public String getEndpointAddress(){
		return endpointAddress; 
	}
	public KeyGenerator getUserKeyGenerator(){
		return userKeyGenerator;
	}
	public KeyGenerator getMailSizeGenerator(){
		return mailSizeGenerator;
	}
	public KeyGenerator getMailCountGenerator(){
		return mailCountGenerator;
	}
	public KeyGenerator getRetrieveCountGenerator(int min, int max){
		
		if(retrieveCountDistribution.equalsIgnoreCase("ycsbZipfian")){
			return new YCSBzipfianGenerator(min, max);
		}else if(retrieveCountDistribution.equalsIgnoreCase("zipfian")){
			return new ZipfKeyGenerator(zipfRetrieve_a, zipfRetrieve_r, min, max, _randomNumberGenerator.nextLong());
		}else{
			return new UniformKeyGenerator(min, max, _randomNumberGenerator.nextLong());
		}
	}
	public KeyGenerator getDeleteCountGenerator(int min, int max){
		
		if(deleteCountDistribution.equalsIgnoreCase("ycsbZipfian")){
			return new YCSBzipfianGenerator(min, max);
		}else if(deleteCountDistribution.equalsIgnoreCase("zipfian")){
			return new ZipfKeyGenerator(zipfDelete_a, zipfDelete_r, min, max, _randomNumberGenerator.nextLong());
		}else{
			return new UniformKeyGenerator(min, max, _randomNumberGenerator.nextLong());
		}
	}
}
