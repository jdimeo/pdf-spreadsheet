package com.github.jdimeo.pdfsheet;

import java.io.IOException;
import java.nio.file.Paths;

import com.elderresearch.commons.lang.Utilities;

import lombok.val;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PDFSheet {
	public static void main(String[] args) throws IOException {
		val in  = new PDFDirectoryPicker(Utilities.first(args)).get();
		val out = Paths.get("out.xlsx");
		
		if (in != null) {
			WriteSheet.write(PDFParser.parseDiretory(in), out);
			log.info("Done.");
		} else {
			log.error("No directory specified");
		}
	}
}
