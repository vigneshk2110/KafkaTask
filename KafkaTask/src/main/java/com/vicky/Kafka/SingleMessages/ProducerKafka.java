package com.vicky.Kafka.SingleMessages;

import java.util.Properties;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerKafka {
	public static void main(String[] args) throws Exception {


		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<String, String>(props);

		String topicName = "orders";
		String key = "key1";
		String value = "value1";

		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topicName, key, value);

		producer.send(record);

		producer.flush();
		producer.close();
	}
}
