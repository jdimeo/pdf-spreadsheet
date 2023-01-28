package com.github.jdimeo.pdfsheet;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
public class PDFDirectoryPicker implements Supplier<Path> {
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// Ignore- just look and feel
		}
	}
	
	private String defPath;
	
	@Override
	public Path get() {
		if (defPath != null && Files.isDirectory(Paths.get(defPath))) {
			return Paths.get(defPath);
		}
		
		val chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("Select PDF directory");
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile().toPath();
		}
		return null;
	}
}
