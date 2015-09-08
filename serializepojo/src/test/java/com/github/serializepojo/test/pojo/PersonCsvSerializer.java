package com.github.serializepojo.test.pojo;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import com.github.serializepojo.BaseSerializer;
import com.github.serializepojo.csv.CsvFormatter;

public class PersonCsvSerializer extends BaseSerializer<Person, CsvFormatter> {

	public PersonCsvSerializer(CsvFormatter formatter) throws IOException {
		super(formatter);
	}

	@Override
	public OutputStream serialize(Collection<Person> pojos) throws IOException {
		this.writeHeader();

		for (Person person : pojos) {
			this.writeRow(person);
		}

		return this.formatter.getStream();
	}

	private void writeHeader() throws IOException {
		this.formatter.startLine();

		this.formatter.startCell();
		this.formatter.writeCell("id");
		this.formatter.endCell();

		this.formatter.startCell();
		this.formatter.writeCell("firstName");
		this.formatter.endCell();

		this.formatter.startCell();
		this.formatter.writeCell("lastName");
		this.formatter.endCell();

		this.formatter.startCell();
		this.formatter.writeCell("birthday");
		this.formatter.endCell();

		this.formatter.startCell();
		this.formatter.writeCell("address");
		this.formatter.endCell();

		this.formatter.endLine();
	}

	private void writeRow(Person person) throws IOException {
		this.formatter.startLine();

		this.formatter.startCell();
		this.formatter.writeCell(person.getId());
		this.formatter.endCell();

		this.formatter.startCell();
		this.formatter.writeCell(person.getFirstName());
		this.formatter.endCell();

		this.formatter.startCell();
		this.formatter.writeCell(person.getLastName());
		this.formatter.endCell();

		this.formatter.startCell();
		this.formatter.writeCell(String.valueOf(person.getBirthday()));
		this.formatter.endCell();

		this.formatter.startCell();
		this.formatter.writeCell(person.getAddress());
		this.formatter.endCell();

		this.formatter.endLine();
	}

}
