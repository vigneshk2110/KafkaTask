package com.vicky.Kafka.Pojo2Avro;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

import com.vicky.Kafka.Order_Pojo;

public class Pojo2AVROschema {

	public static void main(String[] args) throws IOException {
		Schema schema = ReflectData.get().getSchema(Order_Pojo.class);		
		File file = new File("src/main/java/com/vicky/Kafka/Order_Details.avsc");
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.append(schema.toString(true));
		fileWriter.close();
		
	}
}
