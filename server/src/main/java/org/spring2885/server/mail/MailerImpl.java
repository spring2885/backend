package org.spring2885.server.mail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring2885.server.db.model.DbTemplate;
import org.spring2885.server.db.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

@Component("mailer")
public class MailerImpl implements Mailer {
    private static final Logger logger = LoggerFactory.getLogger(MailerImpl.class);
    
    private JavaMailSender sender;
    
    private TemplateService templateService;
    
    
    @Autowired
    public MailerImpl(JavaMailSender javaMailSender, TemplateService templateService) {
        this.sender = javaMailSender;
        this.templateService = templateService;
    }
    
    @Value("${app.mail.from}")
    private String from;
    
    public void send(String email, String templateName, String title, 
            Map<String, String> data) throws IOException, URISyntaxException {
        
        logger.info("Sending mail to: {}, template: {}", email, templateName);
        DbTemplate db = templateService.findById(templateName);
        if (db == null) {
            logger.error("Unable to find template named: {}", templateName);
            // TODO: Should raise an error;
            return;
        }
        
        String templateBody = db.getBody();
        Template tmpl = Mustache.compiler().compile(templateBody); 
        String body = tmpl.execute(data);
        
        logger.info("Sending tempate {}, body: {}", templateName, body);
        
        MimeMessage mail = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(email);
            helper.setReplyTo(from);
            helper.setFrom(from);
            helper.setSubject(title);
            helper.setText(body);
        } catch (MessagingException e) {
            logger.error("Exception sending email to: " + email, e);
        }
        sender.send(mail);  
    }
}
