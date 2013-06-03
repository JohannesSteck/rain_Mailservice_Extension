package radlab.rain.workload.mailapp;

import java.util.Properties;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SmtpAction { 

  public void sendSmtpMail(String host, String from, String to, String mailContent, String mailSubject) throws AddressException, MessagingException{

  //String host = "localhost";
  //String from = "joe@loc.de";
  //String to = "joe@loc.de";

  // Get system properties TODO change?
  Properties properties = System.getProperties(); 

  // Setup mail server
  properties.setProperty("mail.smtp.host", host); 

  // Get the default Session object.
  Session session = Session.getDefaultInstance(properties); 

  // Create a default MimeMessage object.
  MimeMessage message = new MimeMessage(session); 

  // Set the RFC 822 "From" header field using the
  // value of the InternetAddress.getLocalAddress method.
  message.setFrom(new InternetAddress(from));

  // Add the given addresses to the specified recipient type.
  message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

  // Set the "Subject" header field.
  message.setSubject(mailSubject); 

  // Sets the given String as this part's content,
  // with a MIME type of "text/plain".
  message.setText(mailContent); 
  
  //System.out.println("Sending Message with size: "+mailContent.length());
  //System.out.println("Content: \n"+mailContent);
  // Send message
  Transport.send(message);
  //System.out.println("Message with "+mailSubject+" sent via SMTP: from "+from+" to "+to);
  //System.out.println(message);
  }
}