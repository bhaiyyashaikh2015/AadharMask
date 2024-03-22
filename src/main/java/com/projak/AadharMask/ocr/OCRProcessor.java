package com.projak.AadharMask.ocr;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class OCRProcessor {

    private final Tesseract tesseract;

    public OCRProcessor(Tesseract tesseract) {
        this.tesseract = tesseract;
    }

    public String detectText(File imageFile) throws TesseractException {
        return tesseract.doOCR(imageFile);
    }
}
