/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project.attempt.pkg1;

import com.sun.mail.smtp.SMTPTransport;
import static java.lang.Math.random;
import static java.lang.StrictMath.random;
import java.math.BigInteger;
import java.security.Security;
import java.util.Date;
import java.util.Properties;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.objects.NativeMath.random;


public class SendEmail{
    
    private SecureRandom random = new SecureRandom();

    public String sendEmailRegister(String email, String fullname){   
        System.out.println("ppppp");
        final String username = "cinemayosuaenrico@gmail.com";
        final String password = "thomasisbae";
        String code = generateRandomCode(5);

	Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");
	
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
            }
	 });

	try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("cinemayosuaenrico@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
		InternetAddress.parse(email));
            message.setSubject("Welcome to Yosua-Enrico Cinema!");
            message.setText("Dear " + fullname + ","
		+ "\n\nWelcome to Yosua-Enrico Cinema!"
                + "\nHere is your verification code : " + code 
                + "\nRegards, \nYosua-Enrico");
            System.out.println("haha");
            Transport.send(message);
            System.out.println("succes");
            return code;

	} catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, "Failed to send email!");
            return null;
	}
    }
    
    public String sendEmailBooking(String email, String fullname, ArrayList<String> seatNumbers){   
        System.out.println(email + "HAHAHAHAHAAHHA");
        final String username = "cinemayosuaenrico@gmail.com";
        final String password = "thomasisbae";
        String code = generateRandomCode(5);
        System.out.println("1");
	Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");
	       System.out.println("2");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
            }
	 });
        System.out.println("3");
	try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("cinemayosuaenrico@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
		InternetAddress.parse(email));
            
            message.setSubject("Yosua-Enrico Cinema Verification Code");
            message.setText("Dear " + fullname + ","
		
                + "\n\nHere is your verification code for the seats: "
                + "\n "+ seatNumbers.toString()
                + "\n\n " + code    
                + "\n\n\nRegards,\n Yosua-Enrico");
            System.out.println("4");
            Transport.send(message);
            System.out.println("5");
            JOptionPane.showMessageDialog(null, "Email sent !!");
            return code;

	} catch (MessagingException e) {
            JOptionPane.showMessageDialog(null, "Failed to send email!");
            return null;
	}
    }
    
    public String generateRandomCode(int length){
        return new BigInteger(length*5, random).toString(32).toUpperCase();
    }
    
}
