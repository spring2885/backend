package org.spring2885.server.mail;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    @Autowired
    public MailerImpl(JavaMailSender javaMailSender) {
        this.sender = javaMailSender;
    }
    
    @Value("${app.mail.from}")
    private String from;
    
    public void send(String email, String templateName, String title, 
            Map<String, String> data) throws IOException, URISyntaxException {
        
        logger.info("Sending mail to: {}, template: {}", email, templateName);
        String path = "/org/spring2885/server/mail/" + templateName;
        URL u = getClass().getResource(path);
        String text = Streams.asString(u.openStream());
        Template tmpl = Mustache.compiler().compile(text); 
        String body = tmpl.execute(data);
        
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
