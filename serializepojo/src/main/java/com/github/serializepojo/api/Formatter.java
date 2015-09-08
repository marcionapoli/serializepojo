package com.github.serializepojo.api;

import java.io.IOException;
import java.io.OutputStream;

import com.github.serializepojo.csv.CsvFormatter;

/**
 * Formatter sets the object layout to be exported.
 * 
 * @see CsvFormatter
 */
public interface Formatter {

	/**
	 * Initializes the formatter. If you need to instantiate the Stream object,
	 * this is the time.
	 * 
	 * @throws IOException
	 */
	void start() throws IOException;

	/**
	 * Notifies the formatter that a line must be created.
	 * 
	 * @throws IOException
	 */
	void startLine() throws IOException;

	/**
	 * Notifies the formatter that a cell must be created.
	 * 
	 * @throws IOException
	 */
	void startCell() throws IOException;

	/**
	 * Writes the contents in the current cell.
	 * 
	 * @param value
	 * @throws IOException
	 */
	void writeCell(String value) throws IOException;

	/**
	 * Notifies the formatter that the current cell must be finalized.
	 * 
	 * @throws IOException
	 */
	void endCell() throws IOException;

	/**
	 * Notifies the formatter that the current line must be finalized.
	 * 
	 * @throws IOException
	 */
	void endLine() throws IOException;

	/**
	 * Last formatter command. At this time if you have any open resource it
	 * should be closed.
	 * 
	 * @throws IOException
	 */
	void end() throws IOException;

	/**
	 * Returns the stream created with rich content.
	 * 
	 * @return
	 */
	OutputStream getStream();

}
