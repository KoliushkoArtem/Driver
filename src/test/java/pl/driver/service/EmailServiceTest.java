package pl.driver.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

@Slf4j
class EmailServiceTest {

    private JavaMailSender mailSenderMock;
    private EmailService emailService;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        mailSenderMock = mock(JavaMailSender.class);
        emailService = new EmailService(mailSenderMock);

        log.info(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        log.info(String.format("test finished: %s", testInfo.getDisplayName()));
    }

    @Test
    @DisplayName("When call sendEmail method assert that method will be called 1 time")
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