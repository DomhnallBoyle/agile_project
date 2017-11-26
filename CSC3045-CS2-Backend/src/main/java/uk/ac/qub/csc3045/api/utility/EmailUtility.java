package uk.ac.qub.csc3045.api.utility;

import uk.ac.qub.csc3045.api.model.Project;
import uk.ac.qub.csc3045.api.model.User;
import uk.ac.qub.csc3045.api.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class EmailUtility {

private JavaMailSender javaMailSender;
	
	@Autowired
	public EmailUtility(JavaMailSender javaMailSender){
		this.javaMailSender = javaMailSender;
	}
	
	@Async
	public void sendProductOwnerEmail(Project project) throws MailException, InterruptedException {
        Thread.sleep(100);
        SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(project.getProductOwner().getEmail());
		mail.setFrom(SecurityConstants.SERVER_USERNAME);
		mail.setSubject("You have been added as a Product Owner for "+project.getName());
		mail.setText("Hi "+ project.getProductOwner().getForename() + ", \n\nJust to let you know you have been "
				+ "added as Product Owner for "+project.getName()+" \n\nThanks,\nYour Sys Admin Team");
		javaMailSender.send(mail);
	}
	@Async
	public void sendTeamMemberEmails(Project project, User user) throws MailException, InterruptedException {
        Thread.sleep(100);
        SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(user.getEmail());
		mail.setFrom(SecurityConstants.SERVER_USERNAME);
		mail.setSubject("You have been added as a Team Member for "+project.getName());
		mail.setText("Hi "+ user.getForename()+ ", \n\nJust to let you know you have been "
				+ "added to the team for "+project.getName()+" \n\nThanks,\nYour Sys Admin Team");
		javaMailSender.send(mail);
	}
	@Async
	public void sendScrumMasterEmails(Project project, User user) throws MailException, InterruptedException {
        Thread.sleep(100);
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
		mail.setFrom(SecurityConstants.SERVER_USERNAME);
		mail.setSubject("You have been added as a Scrum Master for "+project.getName());
		mail.setText("Hi "+ user.getForename()+ ", \n\nJust to let you know you have been "
				+ "added as a Scrum Master for "+project.getName()+" \n\nThanks,\nYour Sys Admin Team");
		javaMailSender.send(mail);
	}
    
}
