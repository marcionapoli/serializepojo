package com.github.serializepojo.test.mock;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.github.serializepojo.api.Config;
import com.github.serializepojo.csv.CsvFormatter;

/**
 * Formatter mock to retrieve the serialized values.
 */
public class MockFormatter extends CsvFormatter {

	public List<List<String>> matrix = new ArrayList<>();

	public int rows = 0;
	public int cells = 0;

	public MockFormatter() {
		this(null);
	}

	public MockFormatter(Config config) {
		super(config);
	}

	@Override
	public void start() throws IOException {

	}

	@Override
	public void startLine() throws IOException {
		this.rows++;
		this.matrix.add(new ArrayList<>());
	}

	@Override
	public void startCell() throws IOException {
		this.cells++;
	}

	@Override
	public void writeCell(String value) throws IOException {
		List<String> list = this.matrix.get(this.rows - 1);
		list.add(value);
	}

	@Override
	public void endCell() throws IOException {

	}

	@Override
	public void endLine() throws IOException {

	}

	@Override
	public void end() throws IOException {

	}

	@Override
	public OutputStream getStream() {
		return null;
	}

	public String getCell(int row, int column) {
		List<String> cells = this.matrix.get(row);
		if (cells == null) {
			return null;
		}

		return cells.get(column);
	}

}
