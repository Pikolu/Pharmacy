package com.pharmacy.service.impl;

import com.pharmacy.config.Constants;
import com.pharmacy.domain.User;
import com.pharmacy.domain.pojo.ContactForm;
import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

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
    @Autowired
    private JavaMailSender javaMailService;

    @Async
    public void sendContactEmail(ContactForm contactForm, String baseUrl){
        LOG.info("Sending e-mail from '{}'", contactForm.getEmail());
        Locale locale = Locale.GERMAN;
        Context context = new Context(locale);
        context.setVariable("contactForm", contactForm);
        String content = templateEngine.process("contactEmail", context);
        String subject = contactForm.getSubject();
        sendEmail(Constants.RECIPIENT_E_MAIL, subject, content, false, true);
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        LOG.info("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart, isHtml, to, subject, content);
        MimeMessage mimeMessage = javaMailService.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(to);
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailService.send(mimeMessage);
            LOG.info("Sent e-mail to User '{}'", to);
        } catch (Exception e) {
            LOG.warn("E-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
        }
    }

    @Async
    public void sendPasswordResetMail(User user, String baseUrl) {
        LOG.info("Sending password reset e-mail to '{}'", user.getEmail());
        Locale locale = Locale.GERMAN;
        Context context = new Context(locale);
        context.setVariable("user", user);
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process("passwordResetEmail", context);
        String subject = messageSource.getMessage("email.reset.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(User user, String baseUrl) {
        LOG.info("Sending activation e-mail to '{}'", user.getEmail());
        Locale locale = Locale.GERMAN;
        Context context = new Context(locale);
        context.setVariable("user", user);
        context.setVariable("baseUrl", baseUrl);
        String content = templateEngine.process("activationEmail", context);
        String subject = messageSource.getMessage("email.activation.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }
}
