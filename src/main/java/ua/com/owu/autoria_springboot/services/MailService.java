package ua.com.owu.autoria_springboot.services;

//package ua.com.owu.feb_2023_springboot.services;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;
//import lombok.AllArgsConstructor;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//import ua.com.owu.feb_2023_springboot.models.Car;
//
//@Service
//@AllArgsConstructor
//public class MailService {
//    private JavaMailSender javaMailSender;
//
//    public void sendEmail(Car car) {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
//
//        try {
//            helper.setTo("annamukola1978@gmail.com");
//            helper.setText("<h2> car " + car.toString() + " created :)</h2>", true);
//            helper.setFrom(new InternetAddress("annamukola1978@gmail.com"));
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//        javaMailSender.send(mimeMessage);
//    }
//}
