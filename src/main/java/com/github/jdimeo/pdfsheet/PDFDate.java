package com.github.jdimeo.pdfsheet;

import java.nio.file.Path;
import java.util.Date;

import com.elderresearch.commons.lang.extract.Match;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
@AllArgsConstructor(staticName = "of")
@Accessors(fluent = true) @Getter
public class PDFDate {
	private Path file;
	private int page;
	private Match<Date> match;
}
