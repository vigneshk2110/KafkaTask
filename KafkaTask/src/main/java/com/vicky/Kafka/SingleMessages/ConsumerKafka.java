package com.vicky.Kafka.SingleMessages;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.*;

public class ConsumerKafka {
    public static void main(String[] args) throws Exception {
    	
        String topicName = "orders";

        Properties consumerProperties = new Properties();
        consumerProperties.put
        (ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.put
        (ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, 
        		"org.apache.kafka.common.serialization.StringDeserializer");
        consumerProperties.put
        (ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, 
        		"org.apache.kafka.common.serialization.StringDeserializer");
        consumerProperties.put
        (ConsumerConfig.GROUP_ID_CONFIG, "consumer-group");
        consumerProperties.put
        (ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


        KafkaConsumer<String, String> consumer = 
        		new KafkaConsumer<String, String>(consumerProperties);

        consumer.subscribe(Arrays.asList(topicName));

        while (true) {
            ConsumerRecords<String, String> records = 
            		consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n",
                		record.offset(), record.key(), record.value());
            }
        }
    }
}
