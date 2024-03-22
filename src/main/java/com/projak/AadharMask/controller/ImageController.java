package com.projak.AadharMask.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.projak.AadharMask.image.ImageProcessor;
import com.projak.AadharMask.image.RotationUtils;
import com.projak.AadharMask.image.utils.Utils;
import com.projak.AadharMask.ocr.OCRProcessor;

import net.sourceforge.tess4j.TesseractException;

@Controller
public class ImageController {

    private final OCRProcessor ocrProcessor;

    @Autowired
    public ImageController(OCRProcessor ocrProcessor) {
        this.ocrProcessor = ocrProcessor;
    }

    @GetMapping("/")
    public String showUploadForm() {
        return "upload_form";
    }

    @PostMapping("/upload")
    public String processImage(@RequestParam("file") MultipartFile file, Model model) {
        System.out.println("Inside Upload");
        try {
            if (file.isEmpty()) {
                model.addAttribute("error", "Please select an image to upload.");
                return "upload_form";
            }

            BufferedImage originalImage = ImageIO.read(file.getInputStream());

            // Resize the image
            int newWidth = 500;
            int newHeight = 500;
            BufferedImage resizedImage = ImageProcessor.resizeImage(originalImage, newWidth, newHeight);

            // Rotate the image
            double angle = 90.0; // Rotate 90 degrees clockwise
            BufferedImage rotatedImage = RotationUtils.rotateImage(resizedImage, 0);

            // Perform OCR text detection using OCRProcessor
            String extractedText = ocrProcessor.detectText(convertToTempFile(rotatedImage));
            System.out.println(extractedText);
            System.out.println("aadhar Number is ==> "+Utils.extractAadharNumber(extractedText));
            model.addAttribute("extractedText", extractedText);

            // Masking logic
            int maskStartX = 150; // X-coordinate of the top-left corner of the mask
            int maskStartY = 385; // Y-coordinate of the top-left corner of the mask
            int maskWidth = 130; // Width of the mask rectangle
            int maskHeight = 40; // Height of the mask rectangle

            // Create a copy of the rotated image for masking
            BufferedImage maskedImage = new BufferedImage(rotatedImage.getWidth(), rotatedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = maskedImage.createGraphics();
            g2d.drawImage(rotatedImage, 0, 0, null);
            g2d.setColor(Color.BLACK);
            g2d.fillRect(maskStartX, maskStartY, maskWidth, maskHeight);
            g2d.dispose();

            // Convert the masked image to Base64 for display on the client-side
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(maskedImage, "png", baos);
            baos.flush();
            byte[] imageInBytes = baos.toByteArray();
            baos.close();

            String maskedImageData = "data:image/png;base64," + java.util.Base64.getEncoder().encodeToString(imageInBytes);
            model.addAttribute("maskedImageData", maskedImageData);
            model.addAttribute("maskedImageFilename", file.getOriginalFilename());

        } catch (IOException | TesseractException e) {
            e.printStackTrace();
            model.addAttribute("error", "Error processing the image: " + e.getMessage());
        }

        return "result";
    }

    @GetMapping("/downloadMasked/{filename}")
    public ResponseEntity<byte[]> downloadMaskedImage(@PathVariable("filename") String filename) {
        try {
            // Load the masked image file from the server
            File file = new File("C:\\MaskIMG" + filename); // Replace with the actual path to the masked images directory
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Read the file content and prepare the response entity
            byte[] fileContent = FileUtils.readFileToByteArray(file);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .body(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private File convertToTempFile(BufferedImage image) throws IOException {
        File tempFile = File.createTempFile("temp-image-", ".png");
        ImageIO.write(image, "png", tempFile);
        return tempFile;
    }
}
