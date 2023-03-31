package com.vicky.Kafka;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.record.SimpleRecord;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.FileReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.generic.GenericData;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class ProducerKafka {
	public static void main(String[] args) throws IOException {

		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer");
		props.put("schema.registry.url", "http://localhost:8081");

		KafkaProducer<String, GenericRecord> KafkaProducer = new KafkaProducer<String, GenericRecord>(props);

		Schema schema = ReflectData.get().getSchema(Order_Pojo.class);

		GenericRecord record = new GenericData.Record(schema);          

		File avroFile = new File("src/main/java/com/vicky/Kafka/Order Details.avro");

		DatumReader<SimpleRecord> datumReader = new ReflectDatumReader<SimpleRecord>(SimpleRecord.class);
		FileReader<SimpleRecord> dataFileReader = DataFileReader.openReader(avroFile, datumReader);

		while (dataFileReader.hasNext()) {
			record.put( "orders", dataFileReader.next());
		}

		dataFileReader.close();      

		ProducerRecord<String, GenericRecord> kafkaRecord = new ProducerRecord<String, GenericRecord>("orders", "orders", record);

		KafkaProducer.send(kafkaRecord);

		KafkaProducer.close();

	}
}
