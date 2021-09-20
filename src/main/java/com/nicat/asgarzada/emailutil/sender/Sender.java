package com.nicat.asgarzada.emailutil.sender;

import com.nicat.asgarzada.emailutil.core.Email;
import com.nicat.asgarzada.emailutil.core.entity.MailEntity;
import com.nicat.asgarzada.emailutil.sender.smtp.SmtpSender;

/**
 * General interface for sending {@link MailEntity}. Anyone can implement this interface for sending mail via custom channel.
 *
 * @see SmtpSender
 * @author nasgarzada
 * @version 1.0.0
 * @param <T> any class for returning after sending mail
 */
public interface Sender<T> {

    /**
     * General method for sending mail
     * @param mailEntity generated with {@link Email#builder()}
     * @return declared value
     */
    T send(MailEntity mailEntity);

}
