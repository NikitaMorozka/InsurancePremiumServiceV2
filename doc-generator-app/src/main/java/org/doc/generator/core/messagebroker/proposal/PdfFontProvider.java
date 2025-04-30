package org.doc.generator.core.messagebroker.proposal;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class PdfFontProvider {


    public PDFont getRegularFont(PDDocument document) {
        try (InputStream fontStream = getClass().getResourceAsStream("/fonts/Arial.ttf")) {
            if (fontStream == null) {
                throw new RuntimeException("Не найден шрифт Arial.ttf в resources/fonts/");
            }
            return PDType0Font.load(document, fontStream);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить обычный шрифт", e);
        }
    }

    public PDFont getBoldFont(PDDocument document) {
        try (InputStream fontStream = getClass().getResourceAsStream("/fonts/Arial_Bold.ttf")) {
            if (fontStream == null) {
                throw new RuntimeException("Не найден шрифт Arial_Bold.ttf в resources/fonts/");
            }
            return PDType0Font.load(document, fontStream);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить жирный шрифт", e);
        }
    }
}