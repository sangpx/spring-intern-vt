package com.demo.project_intern.cronJob;

import com.demo.project_intern.constant.BorrowStatus;
import com.demo.project_intern.entity.BorrowBookEntity;
import com.demo.project_intern.repository.BorrowBookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendMailCronJob {

    private final BorrowBookRepository borrowBookRepository;
    private final JavaMailSender mailSender;

    // run everyday at 8 am
    @Scheduled(cron = "0 0 8 * * ?")
    @Transactional
    public void sendReminderEmails() {
        log.info("Running reminder email scheduler...");

        LocalDate targetDate = LocalDate.now().plusDays(2);

        List<BorrowBookEntity> borrowBooks = borrowBookRepository
                .findByExpectedReturnDateBetweenAndBorrowStatus(LocalDate.now(), targetDate, BorrowStatus.BORROWED);

        for (BorrowBookEntity borrowBook : borrowBooks) {
            String email = borrowBook.getUser().getEmail();
            if (email != null && !email.isEmpty()) {
                sendEmail(email, borrowBook);
            }
        }
    }

    private void sendEmail(String to, BorrowBookEntity borrowBook) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedDate = borrowBook.getExpectedReturnDate().atStartOfDay().format(formatter);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Thông báo gần đến ngày trả sách");
        message.setText("Chào bạn, \n\n"
                + "Phiếu mượn sách mã: " + borrowBook.getCode()
                + " của bạn sẽ đến hạn trả vào ngày: " + formattedDate + ".\n"
                + "Vui lòng chuẩn bị trả sách đúng hạn để tránh các khoản phí phát sinh.\n\n"
                + "Cảm ơn bạn!");
        mailSender.send(message);
        log.info("Sent Email Successfully!");
    }
}
