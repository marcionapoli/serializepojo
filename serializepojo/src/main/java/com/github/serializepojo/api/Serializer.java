package com.github.serializepojo.api;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

/**
 * Contract common responsibility to set standard methods in the serialization
 * of a flat pojo @param <P> object.
 * 
 */
public interface Serializer<P> {

	/**
	 * see {@link com.github.serializepojo.api.Serializer#serialize(Collection)}
	 * 
	 * @param pojo
	 * @return
	 * @throws IOException
	 */
	OutputStream serialize(P pojo) throws IOException;

	/**
	 * Serializes the object (pojo) according to a set format {@link Formatter}
	 * 
	 * <pre>
	 * RF3 - Return a java.io.OutputStream type of the object as output of transformation.
	 * </pre>
	 * 
	 * @param pojos
	 * @return
	 * @throws IOException
	 */
	OutputStream serialize(Collection<P> pojos) throws IOException;

}
