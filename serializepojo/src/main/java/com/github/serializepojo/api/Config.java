package com.github.serializepojo.api;

import java.nio.charset.Charset;
import java.nio.file.Path;

/**
 * Settings serializer. {@link Serializer}
 */
public class Config {

	public final String delimiter;
	public final Class<? extends Formatter> format;
	public final Path path;
	public final Charset charset;

	public Config(String delimiter, Class<? extends Formatter> layout, Path path, Charset charset) {
		this.delimiter = delimiter;
		this.format = layout;
		this.path = path;
		this.charset = charset;
	}

	/**
	 * Helper to build settings {@link Config}
	 */
	public static class ConfigBuilder {

		String delimiter;
		Class<? extends Formatter> format;
		Path path;
		Charset charset = Charset.defaultCharset();

		/**
		 * Delimiter used between cells.
		 * 
		 * @param delimiter
		 * @return
		 */
		public ConfigBuilder withDelimiter(String delimiter) {
			this.delimiter = delimiter;
			return this;
		}

		/**
		 * File path to create.
		 * 
		 * @param path
		 * @return
		 */
		public ConfigBuilder toPath(Path path) {
			this.path = path;
			return this;
		}

		/**
		 * Charset to be used in writing the text file.
		 * 
		 * @param charset
		 * @return
		 */
		public ConfigBuilder withCharset(Charset charset) {
			this.charset = charset;
			return this;
		}

		/**
		 * Settings {@link Config} used by the Serializer {@link Serializer}
		 */
		public Config build() {
			return new Config(this.delimiter, this.format, this.path, this.charset);
		}

	}
}
