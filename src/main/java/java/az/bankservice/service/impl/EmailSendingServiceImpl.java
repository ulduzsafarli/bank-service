package java.az.bankservice.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.az.bankservice.exception.custom.EmailSendingException;
import java.az.bankservice.model.support.EmailAnswerDto;
import java.az.bankservice.model.support.SupportDto;
import java.az.bankservice.service.NotificationEmailService;
import java.az.bankservice.service.util.SupportEmailService;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSendingServiceImpl implements NotificationEmailService, SupportEmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final Environment environment;

    @Value("${bank.support.form}")
    private String supportForm;

    @Value("${bank.notification.form}")
    private String notificationForm;

    public void sendResponseEmail(String to, EmailAnswerDto emailAnswerDto) {
        sendEmail(to, emailAnswerDto, supportForm, "response");
    }

    public void sendNotificationEmail(String to, EmailAnswerDto emailAnswerDto) {
        sendEmail(to, emailAnswerDto, notificationForm, "notification");
    }

    private void sendEmail(String to, EmailAnswerDto emailAnswerDto, String formType, String emailType) {
        try {
            MimeMessage mimeMessage = createMimeMessage(to, emailAnswerDto.getResponseMessage(), formType);
            sendMimeMessage(mimeMessage);
            log.info("Email sent successfully to: {} for {} type: {}", to, emailType, formType);
        } catch (MessagingException | MailException e) {
            throw new EmailSendingException("Error while sending " + emailType + " email", e);
        }
    }

    public void sendSupportEmail(SupportDto supportDto) {
        try {
            MimeMessage mimeMessage = createSupportMimeMessage(supportDto);
            sendMimeMessage(mimeMessage);
            log.info("Support email sent successfully");
        } catch (MessagingException | MailException e) {
            throw new EmailSendingException("Error while sending support email", e);
        }
    }

    private MimeMessage createMimeMessage(String to, String responseMessage, String subject) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        Context context = new Context();
        context.setVariable("responseMessage", responseMessage);

        String emailContent = templateEngine.process("responseEmail", context);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(emailContent, true);
        return mimeMessage;
    }

    private MimeMessage createSupportMimeMessage(SupportDto supportDto) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        Context context = new Context();
        context.setVariable("supportDto", supportDto);

        String emailContent = templateEngine.process("supportEmail", context);

        helper.setTo(Objects.requireNonNull(environment.getProperty("spring.mail.to")));
        helper.setSubject(supportForm);
        helper.setText(emailContent, true);
        helper.setFrom(supportDto.getEmail());

        return mimeMessage;
    }

    private void sendMimeMessage(MimeMessage mimeMessage) {
        try {
            javaMailSender.send(mimeMessage);
        } catch (MailException e) {
            throw new EmailSendingException("Error while sending email", e);
        }
    }
}
