package com.github.serializepojo.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.serializepojo.ReflectionCsvSerializer;
import com.github.serializepojo.api.Config;
import com.github.serializepojo.csv.CsvFormatter;
import com.github.serializepojo.test.mock.MockFormatter;
import com.github.serializepojo.test.pojo.Person;
import com.github.serializepojo.test.pojo.PersonCsvSerializer;
import com.github.serializepojo.validation.Validations;

public class CsvTestSerializer {

	private static final String OUTPUT_FILE = "./output/test.csv";

	Person person;

	/**
	 * Initializes a simple object for testing.
	 */
	@Before
	public void initialize() {
		person = new Person();
		person.setId("1");
		person.setFirstName("My first name");
		person.setLastName("My last name");
		person.setAddress("My address");
		person.setBirthday(new Date());
	}

	/**
	 * Verifies that the settings are being filled correctly.
	 */
	@Test
	public void testConfigBuilder() {
		//@formatter:off
		Config config = new Config.ConfigBuilder()
				.withDelimiter(",")
				.withCharset(StandardCharsets.UTF_8)
				.toPath(Paths.get(OUTPUT_FILE))
				.build();
		//@formatter:on
		Assert.assertEquals(config.delimiter, ",");
		Assert.assertEquals(config.charset.name(), "UTF-8");
		Assert.assertEquals(config.path.toFile(), new File(OUTPUT_FILE));
	}

	/**
	 * Tests whether the simple types identifier is correct.
	 */
	@Test
	public void testSimpleType() {
		Assert.assertEquals(true, Validations.isSimpleType(String.class));
		Assert.assertEquals(true, Validations.isSimpleType(Boolean.class));
		Assert.assertEquals(true, Validations.isSimpleType(Double.class));
		Assert.assertEquals(true, Validations.isSimpleType(Date.class));
		Assert.assertEquals(false, Validations.isSimpleType(Object.class));
		Assert.assertEquals(false, Validations.isSimpleType(Person.class));
	}

	/**
	 * Serializes using reflection/introspection.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testReflectionSerializer() throws IOException {
		//@formatter:off
		Config config = new Config.ConfigBuilder()
				.withDelimiter(",")
				.withCharset(StandardCharsets.UTF_8)
				.toPath(Paths.get(OUTPUT_FILE))
				.build();
		//@formatter:on

		CsvFormatter formatter = new CsvFormatter(config);

		try (ReflectionCsvSerializer serializer = new ReflectionCsvSerializer(formatter, Person.class)) {
			serializer.serialize(this.person);
		}
	}

	/**
	 * Serializes using a customizable serializer.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testPersonSerializer() throws IOException {
		//@formatter:off
		Config config = new Config.ConfigBuilder()
				.withDelimiter(",")
				.withCharset(StandardCharsets.UTF_8)
				.toPath(Paths.get(OUTPUT_FILE))
				.build();
		//@formatter:on

		CsvFormatter formatter = new CsvFormatter(config);

		try (PersonCsvSerializer serializer = new PersonCsvSerializer(formatter)) {
			serializer.serialize(this.person);
		}
	}

	/**
	 * Verify that the contents exported by serializer that uses reflection is
	 * correct.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testMockReflection() throws IOException {
		MockFormatter formatter = new MockFormatter();

		try (ReflectionCsvSerializer serializer = new ReflectionCsvSerializer(formatter, Person.class)) {
			serializer.serialize(this.person);
		}

		checkResult(formatter);
	}

	/**
	 * Verify that the contents exported by customizable serializer is correct.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testMockPerson() throws IOException {
		MockFormatter formatter = new MockFormatter();

		try (PersonCsvSerializer serializer = new PersonCsvSerializer(formatter)) {
			serializer.serialize(this.person);
		}

		checkResult(formatter);
	}

	/**
	 * Verifies that the exported content is what is stored in the mock
	 * formatter.
	 * 
	 * @param formatter
	 */
	private void checkResult(MockFormatter formatter) {
		Assert.assertEquals(2, formatter.rows);
		Assert.assertEquals(2 * 5, formatter.cells);

		// header
		{
			int col = 0;
			Assert.assertEquals("id", formatter.getCell(0, col++));
			Assert.assertEquals("firstName", formatter.getCell(0, col++));
			Assert.assertEquals("lastName", formatter.getCell(0, col++));
			Assert.assertEquals("birthday", formatter.getCell(0, col++));
			Assert.assertEquals("address", formatter.getCell(0, col++));
		}

		// values
		{
			int col = 0;
			Assert.assertEquals(person.getId(), formatter.getCell(1, col++));
			Assert.assertEquals(person.getFirstName(), formatter.getCell(1, col++));
			Assert.assertEquals(person.getLastName(), formatter.getCell(1, col++));
			Assert.assertEquals(String.valueOf(person.getBirthday()), formatter.getCell(1, col++));
			Assert.assertEquals(person.getAddress(), formatter.getCell(1, col++));
		}
	}

}
