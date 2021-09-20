package com.nicat.asgarzada.emailutil.sender.smtp;

import com.nicat.asgarzada.emailutil.core.Email;
import com.nicat.asgarzada.emailutil.core.entity.AttachmentEntity;
import com.nicat.asgarzada.emailutil.core.entity.MailEntity;
import com.nicat.asgarzada.emailutil.exception.MailMessagingException;
import com.nicat.asgarzada.emailutil.sender.Sender;
import jakarta.activation.DataHandler;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;

import java.util.List;

/**
 * Sender for handling multipart mails. Support for sending list of attachments
 *
 * @author nasgarzada
 * @version 1.0.0
 * @see SmtpSender
 * @see SimpleMessageSender
 * @see Sender
 */
public final class MultipartMessageSender extends SmtpSender {
    public MultipartMessageSender(String propertyPath) {
        super(propertyPath);
    }

    /**
     * Creates mime message object.
     *
     * @param mailEntity generated with {@link Email#builder()}
     * @param session    session of jakarta mail
     * @return mime message
     */
    @Override
    MimeMessage createMimeMessage(MailEntity mailEntity, Session session) {
        var message = new MimeMessage(session);
        try {
            createCommonPart(message, mailEntity);

            var multipart = new MimeMultipart();

            var messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(mailEntity.getMessage(), findMessageType());
            multipart.addBodyPart(messageBodyPart);

            createAttachments(multipart, mailEntity.getAttachments());

            message.setContent(multipart);
        } catch (MessagingException e) {
            throw new MailMessagingException("failed to create multipart message: ", e);
        }
        return message;
    }

    /**
     * Creates list of attachments if exists.
     *
     * @param multipart
     * @param attachments list of attachments
     */
    private void createAttachments(Multipart multipart, List<AttachmentEntity> attachments) {
        attachments.forEach(attachment -> {
            var messageBodyPart = new MimeBodyPart();
            wrapMailException(() -> {
                messageBodyPart.setDataHandler(
                        new DataHandler(
                                new ByteArrayDataSource(attachment.getFile(), attachment.getMimeType())
                        )
                );
                messageBodyPart.setFileName(attachment.getFileName());
                multipart.addBodyPart(messageBodyPart);
            }, "failed to set attachment " + attachment.getFileName());
        });
    }
}
