package study.gonet.mailsample;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @RequestMapping(value = "/write")
    public String write() {
        return "write";
    }

    @RequestMapping(value = "/make")
    public String make(ContentRequestDTO requestDTO, RedirectAttributes redirectAttributes, Model model) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(requestDTO.getEmail())
                .subject("[JAVA STUDY] HTML -> PDF -> 첨부파일 -> 이메일전송 메일")
                .build();

        String message = emailService.sendMail(emailMessage, requestDTO.getContentList());

        model.addAttribute("message", message);
        model.addAttribute("resultList", requestDTO.getContentList());

        return "preview";
    }
}
