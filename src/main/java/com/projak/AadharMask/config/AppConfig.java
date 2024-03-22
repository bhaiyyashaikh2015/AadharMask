package com.projak.AadharMask.config;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Tesseract tesseract() {
        Tesseract tesseract = new Tesseract();
//        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
        tesseract.setDatapath("C:\\Users\\Bhaiyya Shaikh\\AppData\\Local\\Programs\\Tesseract-OCR\\tessdata");
        return tesseract;
    }
}
