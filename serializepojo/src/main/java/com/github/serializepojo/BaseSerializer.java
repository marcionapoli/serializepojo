package com.github.serializepojo;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import com.github.serializepojo.api.Formatter;
import com.github.serializepojo.api.Serializer;

/**
 * Base class to facilitate different implementations.
 * 
 * @see ReflectionCsvSerializer
 */
public abstract class BaseSerializer<P, F extends Formatter> implements Serializer<P>, Closeable {

	protected final F formatter;

	/**
	 * Specifies the formatter to be used {@link Formatter}
	 * 
	 * @param formatter
	 * @throws IOException
	 */
	public BaseSerializer(F formatter) throws IOException {
		this.formatter = formatter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.serializepojo.api.Serializer#serialize(java.lang.Object)
	 */
	@Override
	public OutputStream serialize(final P pojo) throws IOException {
		return this.serialize(Arrays.asList(pojo));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		this.formatter.end();
	}

}
