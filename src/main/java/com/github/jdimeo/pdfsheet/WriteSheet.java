package com.github.jdimeo.pdfsheet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.val;

public class WriteSheet {
	public static void write(List<PDFDate> dates, Path p) throws IOException {
		try (val wb = new XSSFWorkbook()) {
			val s = wb.createSheet("Extracted dates");
			
			val cellStyle = wb.createCellStyle();
			cellStyle.setDataFormat((short)14);
			
			val base = wb.getFontAt(0);
			val bold = wb.createFont();
			bold.setBold(true);
			
			val header = s.createRow(0);
			s.createFreezePane(0, 1);
			header.createCell(0).setCellValue("File");
			header.createCell(1).setCellValue("Page");
			header.createCell(2).setCellValue("Date");
			header.createCell(3).setCellValue("Context");
			
			int i = 1;
			for (val d : dates) {
				val r = s.createRow(i++);
				r.createCell(0).setCellValue(d.file().toString());
				r.createCell(1).setCellValue(d.page());
				
				val dateCell = r.createCell(2);
				dateCell.setCellValue(d.match().getMatch());
				dateCell.setCellStyle(cellStyle);
				
				val rts = new XSSFRichTextString();
				rts.append(d.match().getContextBefore() + " ", base);
				rts.append(d.match().getMatchText(), bold);
				rts.append(" " + d.match().getContextAfter(), base);
				r.createCell(3).setCellValue(rts);				
			}
			
			try (val out = Files.newOutputStream(p)) {
				wb.write(out);	
			}
		}
	}
}
