package com.nicat.asgarzada.emailutil.sender.smtp;

import com.nicat.asgarzada.emailutil.core.Email;
import com.nicat.asgarzada.emailutil.core.entity.MailEntity;
import com.nicat.asgarzada.emailutil.core.entity.RecipientEntity;
import com.nicat.asgarzada.emailutil.exception.FileParseException;
import com.nicat.asgarzada.emailutil.exception.MailMessagingException;
import com.nicat.asgarzada.emailutil.functional.GenericWrapper;
import com.nicat.asgarzada.emailutil.functional.VoidWrapper;
import com.nicat.asgarzada.emailutil.sender.Sender;
import com.nicat.asgarzada.emailutil.util.StringUtil;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.nicat.asgarzada.emailutil.sender.smtp.SmtpProperties.DEFAULT_CHARSET;
import static com.nicat.asgarzada.emailutil.sender.smtp.SmtpProperties.DEFAULT_TYPE;
import static com.nicat.asgarzada.emailutil.sender.smtp.SmtpProperties.HAS_AUTH;
import static com.nicat.asgarzada.emailutil.sender.smtp.SmtpProperties.PASSWORD;
import static com.nicat.asgarzada.emailutil.sender.smtp.SmtpProperties.USERNAME;
import static jakarta.mail.Message.RecipientType.BCC;
import static jakarta.mail.Message.RecipientType.CC;
import static jakarta.mail.Message.RecipientType.TO;

/**
 * Implements {@link Sender} interface for sending mail to smtp server. Supports:
 * <li> Simple mail sending;</li>
 * <li> Multipart mail sending with multiple attachments;</li>
 * @author nasgarzada
 * @version 1.0.0
 * @see SimpleMessageSender
 * @see MultipartMessageSender
 */
public abstract class SmtpSender implements Sender<Void> {
    /**
     * Parsing properties about smtp.
     */
    private final Properties properties;

    /**
     * Constructor
     * @param propertyPath for getting properties of smtp from file.
     * @throws FileParseException if couldn't load properties.
     */
    SmtpSender(String propertyPath) {
        this.properties = new Properties();
        try (var propFile = new FileInputStream(propertyPath)) {
            this.properties.load(propFile);
        } catch (IOException e) {
            throw new FileParseException("failed to parse properties file: ", e);
        }
    }

    /**
     * Implements {@link Sender} interface. Creates mime message from {@link MailEntity}.
     *
     * @param mailEntity generated with {@link Email#builder()}
     * @return void
     */
    @Override
    public Void send(MailEntity mailEntity) {
        var message = createMimeMessage(mailEntity, getSession());
        wrapMailException(() -> Transport.send(message), "failed to transport message");
        return null;
    }

    /**
     * First tries to get value from property. If couldn't gets default value for content type and default charset
     *
     * @return value of content type and default charset for mail
     */
    String findMessageType() {
        var builder = new StringBuilder();
        var defaultType = this.properties.getProperty(DEFAULT_TYPE);
        builder = StringUtil.hasText(defaultType) ?
                builder.append(defaultType) :
                builder.append("text/plain");

        var defaultCharset = this.properties.getProperty(DEFAULT_CHARSET);
        builder.append("; charset=");
        builder = StringUtil.hasText(defaultCharset) ?
                builder.append(defaultCharset) :
                builder.append("UTF-8");
        return builder.toString();
    }

    /**
     * Abstract method that for creating {@link MimeMessage} for sending mail.
     *
     * @param mailEntity generated with {@link Email#builder()}
     * @param session session of jakarta mail
     * @return mime message
     */
    abstract MimeMessage createMimeMessage(MailEntity mailEntity, Session session);

    /**
     * This method used for building common part for both simple and multipart message.
     *
     * @param message mime messageg
     * @param mailEntity generated with {@link Email#builder()}
     * @throws MessagingException
     */
    void createCommonPart(MimeMessage message, MailEntity mailEntity) throws MessagingException {
        message.setFrom(new InternetAddress(mailEntity.getFrom()));
        setRecipients(message, mailEntity.getTo(), TO);
        setRecipients(message, mailEntity.getCc(), CC);
        setRecipients(message, mailEntity.getBcc(), BCC);
        message.setSubject(mailEntity.getSubject());
    }

    /**
     * Helper for wrapping checked exceptions and rethrow with custom exception
     * @param consumer
     * @param message
     * @param <T>
     * @return
     */
    <T> T wrapMailException(GenericWrapper<T> consumer, String message) {
        try {
            return consumer.wrap();
        } catch (Exception e) {
            throw new MailMessagingException(message, e);
        }
    }

    /**
     *  Helper for wrapping checked exceptions and rethrow with custom exception.
     * @param runnable
     * @param message
     */
    void wrapMailException(VoidWrapper runnable, String message) {
        try {
            runnable.wrap();
        } catch (Exception e) {
            throw new MailMessagingException(message, e);
        }
    }

    /**
     * For getting session with authenticator or not.
     * @return {@link Session} of smtp
     */
    private Session getSession() {
        if (checkAuth()) {
            return Session.getDefaultInstance(this.properties, getAuthenticator());
        } else {
            return Session.getDefaultInstance(this.properties);
        }
    }

    /**
     * Checks if smtp authentication enabled or not.
     * @return boolean value if auth enabled or not
     */
    private boolean checkAuth() {
        var hasAuth = this.properties.getProperty(HAS_AUTH);
        return hasAuth.equals("true");
    }

    /**
     * If authentication enabled, with username and password create authenticator
     * @return {@link Authenticator}
     *
     */
    private Authenticator getAuthenticator() {
        var username = this.properties.getProperty(USERNAME);
        var password = this.properties.getProperty(PASSWORD);
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
    }

    /**
     * Sets recipients to {@link MimeMessage} with {@link Message.RecipientType}
     * @param message mime message
     * @param recipientEntities list of recipients addresses.
     * @param type email recipient type. see: {@link Message.RecipientType}
     */
    private void setRecipients(MimeMessage message,
                               List<RecipientEntity> recipientEntities,
                               Message.RecipientType type) {
        var addressList = recipientEntities.stream()
                .filter(Objects::nonNull)
                .map(m -> wrapMailException(() -> new InternetAddress(m.getAddress()),
                        "failed to set " + type + " recipients ")
                )
                .collect(Collectors.toList())
                .toArray(new InternetAddress[recipientEntities.size()]);

        wrapMailException(() -> message.setRecipients(type, addressList), "failed to set " + type);
    }


}
