package com.github.jdimeo.pdfsheet;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jooq.lambda.Seq;

import com.elderresearch.commons.lang.Utilities;
import com.elderresearch.commons.lang.extract.DateExtractor;
import com.elderresearch.commons.lang.extract.LocalityLevel;

import lombok.val;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PDFParser {
	private static final DateExtractor extractor;
	static {
		extractor = DateExtractor.getInstance(LocalityLevel.LOCAL);
		extractor.setCustomFormats(DateExtractor.EXCEL_FORMATS);
	}
	
	public static void parseDiretory(Path p) throws IOException {
		for (val f : Seq.seq(Files.walk(p)).filter(Files::isRegularFile).filter($ -> $.toString().endsWith(".pdf"))) {
			try {
				parse(f);
			} catch (IOException e) {
				log.warn("Error parsing {}: {}", p, Utilities.getMessage(e));
			}
		}
	}
	
	public static void parse(Path p) throws IOException {
		log.info("Parsing {}...", p);
		val ts = new PDFTextStripper();
		try (val pdf = PDDocument.load(p.toFile())) {

			for (var i = 1; i <= pdf.getNumberOfPages(); i++) {
				val out = new StringWriter();
				
				ts.setStartPage(i);
				ts.setEndPage(i);
				ts.writeText(pdf, out);
				
				val pageText = out.toString();
				val dates = extractor.extractAll(pageText);
				
				for (val date : dates) {
					log.info("Page {} date {} context {}[{}]{}", i, date.getMatch(),
						date.getContextBefore(), date.getMatchText(), date.getContextAfter());
				}
				
				i++;
			}
		}
		
	}
}
