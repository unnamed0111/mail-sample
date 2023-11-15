package study.gonet.mailsample;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class PdfRender {
    public ByteArrayOutputStream generatePdfFromHtml(String html) throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver()
                        .addFont(
                                new ClassPathResource("/static/font/NotoSansKR-Regular.ttf")
                                        .getURL()
                                        .toString(),
                                BaseFont.IDENTITY_H,
                                BaseFont.EMBEDDED);

        renderer.setDocumentFromString(html);
        renderer.layout();

        renderer.createPDF(outputStream);

        outputStream.close();

        return outputStream;
    }
}
