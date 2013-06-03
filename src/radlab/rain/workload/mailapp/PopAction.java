package radlab.rain.workload.mailapp;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;

public class PopAction { 

	private MailAppGenerator gen;
	public PopAction(MailAppGenerator gen){
		this.gen=gen;
	}
	
	/*
	public void getAndDeleteAllMessagesPop(String host, String username, String password) throws MessagingException{
	 Properties props = new Properties();

	 
	 
		// props.put("mail.pop3.disablecapa", "true");
		 props.put("mail.pop3.disabletop", "true");
		 
		   // String host = "localhost";
		   // String username = "joe@loc.de";
		   // String password = "mypassword";
		    String provider = "pop3";

		    Session session = Session.getDefaultInstance(props, null);
		    Store store = session.getStore(provider);
		    //System.out.println("connecting to pop3 server...");
		    store.connect(host, username, password);
		    //System.out.println("connected!");
		    
		    //System.out.println("getting inbox...");
		    Folder inbox = store.getFolder("INBOX");
		    if (inbox == null) {
		      System.out.println("No INBOX");
		      System.exit(1);
		    }
		    //System.out.println("getting inbox... done");
		 
		    //System.out.println("opening Folder...");
		    inbox.open(Folder.READ_WRITE);
		    //System.out.println("opened");
	
		    
		    Message[] messages = inbox.getMessages();
		    //System.out.println("printing "+messages.length+" Messages:");
		   
		    ByteArrayOutputStream os =  new ByteArrayOutputStream();
		    for (int i = 0; i < messages.length; i++) {
		      
		        System.out.println("Message " + (i + 1));
		       
		      try {
				messages[i].writeTo(os);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		      //delete received messages
		      messages[i].setFlag(Flags.Flag.DELETED, true);
		      
		    }


		    inbox.close(true);
		    store.close();
	}
	*/
	
	/*
	public void getAndLeaveAllMessagesPop(String host, String username, String password) throws MessagingException{
		 Properties props = new Properties();

		 
		 
			// props.put("mail.pop3.disablecapa", "true");
			 props.put("mail.pop3.disabletop", "true");
			 
			   // String host = "localhost";
			   // String username = "joe@loc.de";
			   // String password = "mypassword";
			    String provider = "pop3";

			    Session session = Session.getDefaultInstance(props, null);
			    Store store = session.getStore(provider);
			    //System.out.println("connecting to pop3 server...");
			    store.connect(host, username, password);
			    //System.out.println("connected!");
			    
			    //System.out.println("getting inbox...");
			    Folder inbox = store.getFolder("INBOX");
			    if (inbox == null) {
			      System.out.println("No INBOX");
			      System.exit(1);
			    }
			    //System.out.println("getting inbox... done");
			 
			    //System.out.println("opening Folder...");
			    inbox.open(Folder.READ_ONLY);
			    //System.out.println("opened");
		
			    ByteArrayOutputStream os =  new ByteArrayOutputStream();
			    Message[] messages = inbox.getMessages();
			   // System.out.println("printing "+messages.length+" Messages:");
			    
			    for (int i = 0; i < messages.length; i++) {
			     // System.out.println("Message " + (i + 1));
			      try {
					messages[i].writeTo(os);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			     
			    }
			      


			    inbox.close(false);
			    store.close();
		}
		  
		  */
		  
	/*
	public void getAllAndDeleteSomeMessagesPop(String host, String username, String password, int deleteCount) throws MessagingException{
		 Properties props = new Properties();

		 
			// props.put("mail.pop3.disablecapa", "true");
		 props.put("mail.pop3.disabletop", "true");
		 
		   // String host = "localhost";
		   // String username = "joe@loc.de";
		   // String password = "mypassword";
		    String provider = "pop3";

		    Session session = Session.getDefaultInstance(props, null);
		    Store store = session.getStore(provider);
		    //System.out.println("connecting to pop3 server...");
		    store.connect(host, username, password);
		    //System.out.println("connected!");
		    
		    //System.out.println("getting inbox...");
		    Folder inbox = store.getFolder("INBOX");
		    if (inbox == null) {
		      System.out.println("No INBOX");
		      System.exit(1);
		    }
		    //System.out.println("getting inbox... done");
		 
		    //System.out.println("opening Folder...");
		    inbox.open(Folder.READ_WRITE);
		    //System.out.println("opened");
	
	
		    Message[] messages = inbox.getMessages();
		    //System.out.println("printing "+messages.length+" Messages:");
		   
		    ByteArrayOutputStream os =  new ByteArrayOutputStream();
		    for (int i = 0; i < messages.length; i++) {
		      
		        System.out.println("Message " + (i + 1));
		       
		      try {
				messages[i].writeTo(os);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    if(i<deleteCount){
			      messages[i].setFlag(Flags.Flag.DELETED, true);
				}

		    }


		    inbox.close(true);
		    store.close();

		}
*/
	
	/*
	//Retrieve certain number of messages and delete certain number of messages (<=retrieveCount)
	public void getSomeAndDeleteSomeMessagesPop(String host, String username, String password, int retrieveCount, int deleteCount) throws MessagingException{
		 Properties props = new Properties();

		 
			// props.put("mail.pop3.disablecapa", "true");
		 props.put("mail.pop3.disabletop", "true");
		 
		   // String host = "localhost";
		   // String username = "joe@loc.de";
		   // String password = "mypassword";
		    String provider = "pop3";

		    Session session = Session.getDefaultInstance(props, null);
		    Store store = session.getStore(provider);
		    //System.out.println("connecting to pop3 server...");
		    store.connect(host, username, password);
		    //System.out.println("connected!");
		    
		    //System.out.println("getting inbox...");
		    Folder inbox = store.getFolder("INBOX");
		    if (inbox == null) {
		      System.out.println("No INBOX");
		      System.exit(1);
		    }
		    //System.out.println("getting inbox... done");
		 
		    //System.out.println("opening Folder...");
		    inbox.open(Folder.READ_WRITE);
		    //System.out.println("opened");
	
	
		    Message[] messages = inbox.getMessages();
		    //System.out.println("printing "+messages.length+" Messages:");
		   
		    ByteArrayOutputStream os =  new ByteArrayOutputStream();
		    
		    int retrieveLimit;
		    int messageCount = messages.length;
		    if(messageCount<=retrieveCount){
		    	retrieveLimit=messageCount;
		    }else{
		    	retrieveLimit=retrieveCount;
		    }
		    
		    for (int i = 0; i < retrieveLimit; i++) {
		      
		        System.out.println("Message " + (i + 1));
		       
		      try {
				messages[i].writeTo(os);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    if(i<deleteCount){
			      messages[i].setFlag(Flags.Flag.DELETED, true);
				}

		    }


		    inbox.close(true);
		    store.close();

		}
	*/
	
	/*
	//Retrieve a number of messages and delete a number of messages (<=retrieveCount). Numbers are generated
	public void getSomeAndDeleteSomeMessagesPop(String host, String username, String password) throws MessagingException{
		 Properties props = new Properties();

		 
			// props.put("mail.pop3.disablecapa", "true");
		 props.put("mail.pop3.disabletop", "true");
		 
		   // String host = "localhost";
		   // String username = "joe@loc.de";
		   // String password = "mypassword";
		    String provider = "pop3";

		    Session session = Session.getDefaultInstance(props, null);
		    Store store = session.getStore(provider);
		    //System.out.println("connecting to pop3 server...");
		    store.connect(host, username, password);
		    //System.out.println("connected!");
		    
		    //System.out.println("getting inbox...");
		    Folder inbox = store.getFolder("INBOX");
		    if (inbox == null) {
		      System.out.println("No INBOX");
		      return;
		    }
		    //System.out.println("getting inbox... done");
		 
		    //System.out.println("opening Folder...");
		    inbox.open(Folder.READ_WRITE);
		    //System.out.println("opened");
	
	
		    Message[] messages = inbox.getMessages();
		    //System.out.println("printing "+messages.length+" Messages:");
		   
		    ByteArrayOutputStream os =  new ByteArrayOutputStream();
		    
		    int retrieveLimit;
		    int messageCount = messages.length;
		    
		    //generate number of messages to retrieve
		    int retrieveCount = gen.getRetrieveCountGenerator(1, messageCount).generateKey();
		    
		    //generate number of messages <= retrieveCount to mark for deletion
		    int deleteCount = gen.getDeleteCountGenerator(0, retrieveCount).generateKey();
		    
		    if(messageCount<=retrieveCount){
		    	retrieveLimit=messageCount;
		    }else{
		    	retrieveLimit=retrieveCount;
		    }
		    
		    for (int i = 0; i < retrieveLimit; i++) {
		      
		        System.out.println("Message " + (i + 1));
		       
		      try {
				messages[i].writeTo(os);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		    if(i<deleteCount){
			      messages[i].setFlag(Flags.Flag.DELETED, true);
				}

		    }


		    inbox.close(true);
		    store.close();

		}
*/
	public void getSomeMessagesPop(String host, String username, String password) throws MessagingException{
		 Properties props = new Properties();

		 
			// props.put("mail.pop3.disablecapa", "true");
		 props.put("mail.pop3.disabletop", "true");
		 
		   // String host = "localhost";
		   // String username = "joe@loc.de";
		   // String password = "mypassword";
		    String provider = "pop3";

		    Session session = Session.getDefaultInstance(props, null);
		    Store store = session.getStore(provider);
		    //System.out.println("connecting to pop3 server...");
		    store.connect(host, username, password);
		    //System.out.println("connected!");
		    
		    //System.out.println("getting inbox...");
		    Folder inbox = store.getFolder("INBOX");
		    if (inbox == null) {
		      System.out.println("No INBOX");
		      return;
		    }

		    inbox.open(Folder.READ_ONLY);
		    
		    Message[] messages = inbox.getMessages();
		    //System.out.println("printing "+messages.length+" Messages:");
		   
		    //UIDL
		    FetchProfile fp = new FetchProfile();
		    fp.add(UIDFolder.FetchProfileItem.UID);		    
		    inbox.fetch(messages, fp);
		    
		    ByteArrayOutputStream os =  new ByteArrayOutputStream();
		    
		    int retrieveLimit;
		    int messageCount = messages.length;
		    
		    //generate number of messages to retrieve
		    int retrieveCount = gen.getRetrieveCountGenerator(1, messageCount).generateKey();
		   System.out.println("messageCount: "+messageCount+"retrieve count: "+retrieveCount);
		    
		    if(messageCount<=retrieveCount){
		    	retrieveLimit=messageCount;
		    }else{
		    	retrieveLimit=retrieveCount;
		    }
		    
		    for (int i = 0; i < retrieveLimit; i++) { 
		       
		       
		      try {
		    	  //retrieve message i
				messages[i].writeTo(os);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    }


		    inbox.close(false);
		    store.close();

		}
	
	public void deleteSomeMessagesPop(String host, String username, String password) throws MessagingException {
		 Properties props = new Properties();

		 
			// props.put("mail.pop3.disablecapa", "true");
		 props.put("mail.pop3.disabletop", "true");
		 
		   // String host = "localhost";
		   // String username = "joe@loc.de";
		   // String password = "mypassword";
		    String provider = "pop3";

		    Session session = Session.getDefaultInstance(props, null);
		    Store store = session.getStore(provider);
		    //System.out.println("connecting to pop3 server...");
		    store.connect(host, username, password);
		    //System.out.println("connected!");
		    
		    //System.out.println("getting inbox...");
		    Folder inbox = store.getFolder("INBOX");
		    if (inbox == null) {
		      System.out.println("No INBOX");
		      return;
		    }
		    //System.out.println("getting inbox... done");
		 
		    //System.out.println("opening Folder...");
		    inbox.open(Folder.READ_WRITE);
		    //System.out.println("opened");
	
	
		    Message[] messages = inbox.getMessages();
		
		    
		    int messageCount = messages.length;
		    
		    //UIDL
		    FetchProfile fp = new FetchProfile();
		    fp.add(UIDFolder.FetchProfileItem.UID);		    
		    inbox.fetch(messages, fp);
		      
		    //generate number of messages <= retrieveCount to mark for deletion
		    int deleteCount = gen.getDeleteCountGenerator(0, messageCount).generateKey();
		    System.out.println("messageCount: "+messageCount+" delete count: "+deleteCount);
		    
		    for (int i = 0; i < deleteCount; i++) {
			
		    		//mark message i for deletion
			      messages[i].setFlag(Flags.Flag.DELETED, true);
				

		    }

		    //tell Server to delete marked messages after QUIT
		    inbox.close(true);
		    store.close();

		
	}

	public void emptyPop(String host, String user, String password) throws MessagingException {
		 Properties props = new Properties();

		 
			// props.put("mail.pop3.disablecapa", "true");
		 props.put("mail.pop3.disabletop", "true");
		 

		    String provider = "pop3";

		    Session session = Session.getDefaultInstance(props, null);
		    Store store = session.getStore(provider);

		    store.connect(host, user, password);

		    
		    //System.out.println("getting inbox...");
		    Folder inbox = store.getFolder("INBOX");
		    if (inbox == null) {
		      System.out.println("No INBOX");
		      return;
		    }

		    inbox.open(Folder.READ_ONLY);
		    Message[] messages = inbox.getMessages();
		    //UIDL
		    FetchProfile fp = new FetchProfile();
		    fp.add(UIDFolder.FetchProfileItem.UID);		    
		    inbox.fetch(messages, fp);
		    
		    inbox.close(false);
		    store.close();
		    

		
	}
	
}