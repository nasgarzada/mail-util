package com.nicat.asgarzada.emailutil.sender.smtp;

import com.nicat.asgarzada.emailutil.core.entity.MailEntity;
import com.nicat.asgarzada.emailutil.exception.MailMessagingException;
import com.nicat.asgarzada.emailutil.sender.Sender;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;

/**
 * Handles simple text mails.
 * @see SmtpSender
 * @see MultipartMessageSender
 * @see Sender
 * @author nasgarzada
 * @version 1.0.0
 */
public final class SimpleMessageSender extends SmtpSender {
    public SimpleMessageSender(String propertyPath) {
        super(propertyPath);
    }

    @Override
    MimeMessage createMimeMessage(MailEntity mailEntity, Session session) {
        var message = new MimeMessage(session);
        try {
            createCommonPart(message, mailEntity);
            message.setText(mailEntity.getMessage());
        } catch (MessagingException e) {
            throw new MailMessagingException("failed to create simple message: ", e);
        }
        return message;
    }
}
