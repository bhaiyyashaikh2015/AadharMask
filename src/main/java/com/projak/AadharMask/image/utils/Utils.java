package com.projak.AadharMask.image.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.Word;

public class Utils {

	public static String extractAadharNumber(String fullString) {
		String regexPattern = "\\b\\d{4} \\d{4} \\d{4}\\b";
		Pattern pattern = Pattern.compile(regexPattern);
		Matcher matcher = pattern.matcher(fullString);
		String aadharNumber = "";
		while (matcher.find()) {
			aadharNumber = matcher.group();
		}
		return aadharNumber;
	}

	public static void getAadharNumberPosition() {
		
	}

	public static void main(String[] args) {
		getAadharNumberPosition();
		System.out.println(extractAadharNumber("Sample text with the pattern 1111 2222 3333 and 1234 5678 9101"));
	}
}
