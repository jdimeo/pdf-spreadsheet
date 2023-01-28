package com.github.jdimeo.pdfsheet;

import java.io.IOException;
import java.nio.file.Paths;

public class PDFSheeet {

	public static void main(String[] args) throws IOException {
		PDFParser.parseDiretory(Paths.get(args[0]));
	}
}
