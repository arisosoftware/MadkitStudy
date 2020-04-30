package com.ariso.simlib.report;

import java.io.FileWriter;
import java.io.IOException;
import java.util.EventListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ariso.simlib.EventHandler;
import com.ariso.simlib.model.Model;

/**
 * Class that handles the CSV output.
 *
 * @author <a href="mailto:arisosoftware@gmail.com">Ariso software ltd</a>
 */
public class CsvExporter implements EventHandler {

	private final String separator;
	private final String csvFile;
	private final StringBuilder sb;

	/**
	 * Constructor.
	 *
	 * @param csvFile   the path to the csv file.
	 * @param separator the separator string.
	 */
	public CsvExporter(String csvFile, String separator) {
		this.separator = separator;
		this.csvFile = csvFile;
		this.sb = new StringBuilder();
	}

	/**
	 * Method to write all values for one timestep to the CSV.
	 *
	 * @param modelEntityValues values of the model entities.
	 */
	private void writeTimeStepValues(List<String> modelEntityValues) {
		boolean first = true;

		for (String value : modelEntityValues) {
			if (!first) {
				this.sb.append(this.separator);
			}
			this.sb.append(value);

			first = false;
		}
		sb.append("\n");

	}

	/**
	 * Method to save the data to CSV file.
	 */
	private void saveFile() {
		try {
			try (FileWriter writer = new FileWriter(this.csvFile)) {
				writer.append(this.getString());
				writer.flush();
				Logger.getLogger(CsvExporter.class.getName()).log(Level.SEVERE, null, "File saved");
			}
		} catch (IOException ex) {
			Logger.getLogger(CsvExporter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * @return CSV content
	 */
	private String getString() {
		int last = sb.lastIndexOf("\n");
		if (last >= 0 && sb.length() - last == 1) {
			sb.delete(last, sb.length());
		}
		return sb.toString();
	}

	/**
	 * Method to clear the CSV content.
	 */
	private void clearContent() {
		sb.delete(0, sb.length());
	}

	@Override
	public void open(Model model) {
		clearContent();
		writeTimeStepValues(model.getModelEntitiesKeys());
	}

	@Override
	public void execStep(Model model) {
		writeTimeStepValues(model.getModelEntitiesValues());
	}

	@Override
	public void close(Model model) {
		saveFile();
	}
}
