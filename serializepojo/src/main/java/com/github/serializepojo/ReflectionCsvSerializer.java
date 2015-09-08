package com.github.serializepojo;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Collection;

import com.github.serializepojo.csv.CsvFormatter;
import com.github.serializepojo.validation.Validations;

/**
 * Class that uses the reflection/introspection to serialize the pojo object.
 */
public class ReflectionCsvSerializer extends BaseSerializer<Object, CsvFormatter> {

	protected final Class<?> clazz;
	protected Field[] fields;

	public ReflectionCsvSerializer(CsvFormatter formatter, Class<?> clazz) throws IOException {
		super(formatter);
		this.clazz = clazz;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.serializepojo.api.Serializer#serialize(java.util.Collection)
	 */
	@Override
	public OutputStream serialize(Collection<Object> pojos) throws IOException {
		this.fields = this.clazz.getDeclaredFields();

		try {
			// header
			this.formatter.startLine();
			for (Field field : this.fields) {
				field.setAccessible(true);
				validate(field);
				this.formatter.startCell();
				this.formatter.writeCell(field.getName());
				this.formatter.endCell();
			}
			this.formatter.endLine();

			// rows
			for (Object pojo : pojos) {
				this.formatter.startLine();
				for (Field field : this.fields) {
					this.formatter.startCell();
					this.formatter.writeCell(String.valueOf(field.get(pojo)));
					this.formatter.endCell();
				}
				this.formatter.endLine();
			}

		} catch (Exception e) {
			throw new IOException(e);
		}
		return this.formatter.getStream();
	}

	/**
	 * Checks if the field type can be serialized.
	 * 
	 * @param field
	 * @throws Exception
	 */
	private static void validate(Field field) throws Exception {
		if (!Validations.isSimpleType(field.getType())) {
			throw new Exception("The attribute " + field.getName() + " isn't a simple type.");
		}
	}

}
