package com.pharmacy.service.impl;

import com.pharmacy.config.Constants;
import com.pharmacy.domain.pojo.ContactForm;
import com.pharmacy.service.api.MailService;
import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.activation.FileTypeMap;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import java.util.Locale;
import java.util.Properties;

/**
 * Pharmacy GmbH
 * Created by Alexander on 12.03.2016.
 */
@Service
public class MailServiceImpl {

    private final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);

    @Inject
    private SpringTemplateEngine templateEngine;

    @Inject
    private MessageSource messageSource;

    @Async
    public void sendContactEmail(ContactForm contactForm, String baseUrl){
        LOG.debug("Sending e-mail from '{}'", contactForm.getEmail());
        Locale locale = Locale.GERMAN;
        Context context = new Context(locale);
        String content = templateEngine.process("activationEmail", context);
        String subject = messageSource.getMessage("email.activation.title", null, locale);
        sendEmail(Constants.RECIPIENT_E_MAIL, subject, content, false, true);
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        LOG.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);

        Properties props = System.getProperties();
        String host = "smtp-relay.gmail.com";
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", to);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

//         Prepare message using a Spring helper
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setPassword("K.568136");

        javaMailSender.setJavaMailProperties(props);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(to);
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            LOG.debug("Sent e-mail to User '{}'", to);
        } catch (Exception e) {
            LOG.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }
}
