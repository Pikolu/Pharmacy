package com.pharmacy.service.api;

import com.pharmacy.domain.pojo.ContactForm;

/**
 * Pharmacy GmbH
 * Created by Alexander on 12.03.2016.
 */
public interface MailService {

    void sendContactEmail(ContactForm contactForm, String baseUrl);
}
