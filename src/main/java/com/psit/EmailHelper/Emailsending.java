package com.psit.EmailHelper;



	// Java Program to Illustrate Creation Of
	// Service implementation class
	 

	 
	// Importing required classes
	
	import java.io.File;
import java.io.InputStream;

import javax.mail.MessagingException;
	import javax.mail.internet.MimeMessage;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
	import org.springframework.mail.javamail.JavaMailSender;
	import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.psit.entity.Employee;
	 
	// Annotation
	
	// Class
	// Implementing EmailService interface
@Configuration
	public class Emailsending {
	 
	    @Autowired private JavaMailSender javaMailSender;
	 
	    @Value("${spring.mail.username}") private String sender;
	 
	    // Method 1
	    // To send a simple email
	    public String sendSimpleMail(Employee employee)
	    {
	 
	        // Try block to check for exceptions
	        try {
	 
	            // Creating a simple mail message
	            SimpleMailMessage mailMessage
	                = new SimpleMailMessage();
	 
	            // Setting up necessary details
	            mailMessage.setFrom(sender);
	            mailMessage.setTo(employee.getEmail());
	            mailMessage.setText(employee.getFirstName()+" "+employee.getLastName()+" "+employee.getUrl()+""+employee.getEmail());;
	            mailMessage.setSubject("birthday wishes");
	 
	            // Sending the mail
	            javaMailSender.send(mailMessage);
	            return "Mail Sent Successfully...";
	        }
	 
	        // Catch block to handle the exceptions
	        catch (Exception e) {
	            return "Error while Sending Mail";
	        }
	    }
	 
	    // Method 2
	    // To send an email with attachment
	   /* public String
	    sendMailWithAttachment(Employee employee)
	    {
	        // Creating a mime message
	        MimeMessage mimeMessage
	            = javaMailSender.createMimeMessage();
	        MimeMessageHelper mimeMessageHelper;
	 
	        try {
	 
	            // Setting multipart as true for attachments to
	            // be send
	            mimeMessageHelper
	                = new MimeMessageHelper(mimeMessage, true);
	            mimeMessageHelper.setFrom(sender);
	            mimeMessageHelper.setTo(employee.getEmail());
	            mimeMessageHelper.setText("wish you very happy birthday");
	            mimeMessageHelper.setSubject("birthday wishes");
	 
	            // Adding the attachment
	            FileSystemResource file
	                = new FileSystemResource(
	                    new File(details.getAttachment()));
	 
	            mimeMessageHelper.addAttachment(
	                file.getFilename(), file);
	 
	            // Sending the mail
	            javaMailSender.send(mimeMessage);
	            return "Mail sent Successfully";
	        }
	 
	        // Catch block to handle MessagingException
	        catch (MessagingException e) {
	 
	            // Display message when exception occurred
	            return "Error while sending mail!!!";
	        }
	    }

	

	
*/
		
	
}
