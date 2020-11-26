package pl.driver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

class EmailServiceTest {

    private JavaMailSender mailSenderMock;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        mailSenderMock = mock(JavaMailSender.class);
        emailService = new EmailService(mailSenderMock);
    }

    @Test
    void sendEmail() {
        String to = "TO";
        String subject = "SUBJECT";
        String text = "TEXT";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailService.sendEmail(to, subject, text);

        verify(mailSenderMock, times(1)).send(message);
    }
}