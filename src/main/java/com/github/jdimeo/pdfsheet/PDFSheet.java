package com.github.jdimeo.pdfsheet;

import java.io.IOException;
import java.nio.file.Paths;

public class PDFSheet {
	public static void main(String[] args) throws IOException {
		WriteSheet.write(PDFParser.parseDiretory(Paths.get(args[0])), Paths.get("out.xlsx"));
	}
}
