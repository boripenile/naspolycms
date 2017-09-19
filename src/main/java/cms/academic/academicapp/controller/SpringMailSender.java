package cms.academic.academicapp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cms.academic.academicapp.model.Mail;

@Controller("springMailSender")
public class SpringMailSender {

	private SimpleMailMessage simpleMailMessage;

	private JavaMailSenderImpl javaMailSender;

	@Autowired
	public SpringMailSender(SimpleMailMessage simpleMailMessage, JavaMailSenderImpl javaMailSender) {
		this.simpleMailMessage = simpleMailMessage;
		this.javaMailSender = javaMailSender;
	}

	@Async
	public void sendMail(String salution, String closure, Mail mail, final CommonsMultipartFile attachFile) {
		String fromEmail = mail.getSender();
		String[] toEmail = mail.getRecepients();
		String emailSubject = mail.getSubject();
		String emailBody = String.format(simpleMailMessage.getText(), salution, mail.getBody(), closure);

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		System.out.println("Mail sending started...");
		try {		
            for(int i = 0; i < toEmail.length; i++){
            	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            	helper.setFrom(fromEmail);
    			helper.setTo(toEmail[i]);
    			helper.setSubject(emailSubject);
    			helper.setText(emailBody);
                if(attachFile != null){
                	String attachName = attachFile.getOriginalFilename();
        			if (!attachFile.equals("")) {
        				helper.addAttachment(attachName, new InputStreamSource() {
        					@Override
        					public InputStream getInputStream() throws IOException {
        						return attachFile.getInputStream();
        					}
        				});
        			}
                }	         
    			javaMailSender.send(mimeMessage);
            }
			
		} catch (MessagingException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		try {
			TimeUnit.SECONDS.sleep(15);
		} catch (InterruptedException e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Mail sent successfully.");

	}

}
