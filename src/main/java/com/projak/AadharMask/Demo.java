//package com.projak.AadharMask;
//
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//import javax.imageio.ImageIO;
//
//import net.sourceforge.tess4j.ITesseract;
//import net.sourceforge.tess4j.Tesseract;
//import net.sourceforge.tess4j.Word;
//
//public class Demo {
//
//	public static void main(String[] args) {
//		String tessDataPath = "C:\\Users\\Bhaiyya Shaikh\\AppData\\Local\\Programs\\Tesseract-OCR\\tessdata"; // Replace
//																												// with
//																												// the
//																												// path
//																												// to
//																												// the
//																												// tessdata
//																												// folder
//		File imageFile = new File("C:\\Users\\Bhaiyya Shaikh\\Downloads\\sample-aadhaar-card.png"); // Replace with the
//																									// path to your
//																									// image file
//		String targetString = "0000 1111 2222"; // Specify the target string you want to find
//
//		ITesseract tesseract = new Tesseract();
//		tesseract.setDatapath(tessDataPath);
//
//		try {
//			// Load the image from the file
//			BufferedImage image = ImageIO.read(imageFile);
//
//			// Perform OCR on the image and get the list of words with their bounding boxes
//			List<Word> words = tesseract.getWords(image, ITesseract.RenderedFormat.TEXT);
//
//			// Loop through the extracted words and find the bounding box of the target
//			// string
//			for (Word word : words) {
//				if (word.getText().equals(targetString)) {
//					System.out.println("Found target string: " + targetString);
//					System.out.println("Bounding box: (x, y, width, height) = (" + word.getBoundingBox().x + ", "
//							+ word.getBoundingBox().y + ", " + word.getBoundingBox().width + ", "
//							+ word.getBoundingBox().height + ")");
//					break; // Assuming there is only one occurrence of the target string in the image
//				}
//			}
//		} catch (IOException e) {
//			System.err.println("Error during OCR: " + e.getMessage());
//		}
//	}
//}
