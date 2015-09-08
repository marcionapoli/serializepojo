package com.github.serializepojo.csv;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.serializepojo.api.Config;
import com.github.serializepojo.api.Formatter;

/**
 * Class responsible for formatting the layout as CSV format.
 * 
 * @see Formatter
 */
public class CsvFormatter implements Formatter {

	final Config config;

	protected Writer writer;

	protected OutputStream output;

	protected boolean started = false;

	private static final Logger logger = Logger.getLogger(CsvFormatter.class.getName());

	public CsvFormatter(final Config config) {
		this.config = config;
	}

	@Override
	public void start() throws IOException {
		if (!this.started) {
			this.output = Files.newOutputStream(this.config.path);
			try {
				this.writer = new OutputStreamWriter(this.output, this.config.charset);
				this.writer = new BufferedWriter(this.writer);
				this.started = true;
				if (logger.isLoggable(Level.INFO)) {
					logger.info("CsvFormatter is started!");
				}
			} catch (Exception e) {
				this.output.close();
				if (logger.isLoggable(Level.SEVERE)) {
					logger.severe("CsvFormatter cannot be started! " + e.getMessage());
				}
				throw new IOException(e);
			}
		}
	}

	@Override
	public void startLine() throws IOException {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("CsvFormatter started a new line!");
		}

		if (!this.started) {
			this.start();
		} else {
			this.writer.append(System.lineSeparator());
		}
	}

	@Override
	public void startCell() throws IOException {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("CsvFormatter started a new cell!");
		}
	}

	@Override
	public void writeCell(String value) throws IOException {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("CsvFormatter wrote in the cell! (" + value + ")");
		}

		this.start();
		this.writer.append(value);
	}

	@Override
	public void endCell() throws IOException {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("CsvFormatter finalized the cell!");
		}

		this.start();
		this.writer.append(this.config.delimiter);
	}

	@Override
	public void endLine() throws IOException {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("CsvFormatter completed the line!");
		}
	}

	@Override
	public void end() throws IOException {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("CsvFormatter completed the job!");
		}

		if (this.writer == null) {
			return;
		}

		try {
			this.writer.close();
		} finally {
			this.writer = null;
		}
	}

	@Override
	public OutputStream getStream() {
		return this.output;
	}

}
