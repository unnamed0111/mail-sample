package study.gonet.mailsample;

import com.lowagie.text.DocumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final PdfRender pdfRender;

    public String sendMail(EmailMessage emailMessage, List<ContentDTO> contentList) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo());
            mimeMessageHelper.setSubject(emailMessage.getSubject());

            String content = this.getContent(contentList);

            ByteArrayOutputStream os = pdfRender.generatePdfFromHtml(content);
            ByteArrayResource pdfSrc = new ByteArrayResource(os.toByteArray());

            mimeMessageHelper.setText(content, true);
            mimeMessageHelper.addAttachment("이력서_샘플.pdf", pdfSrc);
            javaMailSender.send(message);

            log.info("SUCCESS");
            return "SUCCESS";
        } catch (MessagingException e) {
            log.info("FAIL");
            throw new RuntimeException(e);
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String getContent(List<ContentDTO> contentList) {
        Context context = new Context();
        context.setVariable("resultList", contentList);
        return templateEngine.process("email", context);
    }
}
