
package com.vicky.Kafka;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.parsing.Parser;
import org.apache.avro.reflect.ReflectData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class CsvToAvroConverter {

	public static void main(String[] args) throws IOException {
		
		File csvFile = new File("src/main/java/com/vicky/Kafka/Order Details.csv");
		File avroFile = new File("src/main/java/com/vicky/Kafka/Order Details.avro");
		
		Schema schema = ReflectData.get().getSchema(Order_Pojo.class);
		
//		Schema schema = new Schema.Parser().
//				parse("src/main/java/com/vicky/Kafka/Pojo2Avro/Order_Details.avsc");

		
		CSVParser csvParser = new CSVParser(new FileReader(csvFile), CSVFormat.DEFAULT);
		DataFileWriter<GenericRecord> avroWriter = new DataFileWriter<GenericRecord>(new GenericDatumWriter<GenericRecord>(schema));
		avroWriter.create(schema, avroFile);

		for (CSVRecord record : csvParser) {
			String Order_ID = record.get(0);
			double Amount = Double.parseDouble(record.get(1));
			double Profit = Double.parseDouble(record.get(2));
			int Quantity = Integer.parseInt(record.get(3));
			String Category = record.get(4);
			String Sub_Category = record.get(5);

			GenericRecord avroRecord = new GenericData.Record(schema);
			avroRecord.put("Order_ID", Order_ID);
			avroRecord.put("Amount", Amount);
			avroRecord.put("Profit", Profit);
			avroRecord.put("Quantity", Quantity);
			avroRecord.put("Category", Category);
			avroRecord.put("Sub_Category", Sub_Category);

			avroWriter.append(avroRecord);
		}

		csvParser.close();
		avroWriter.close();
	}
}

