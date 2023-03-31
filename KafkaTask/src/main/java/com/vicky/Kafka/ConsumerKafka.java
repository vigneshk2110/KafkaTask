package com.vicky.Kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

public class ConsumerKafka {

  public static void main(String[] args) {
	  
    String topicName = "orders";

    Properties properties = new Properties();
    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer_group");
    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer");
    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer");
    properties.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
    properties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

    KafkaConsumer<String, GenericRecord> consumer = new KafkaConsumer<>(properties);
    
    consumer.subscribe(Collections.singleton(topicName));
    
    ArrayList<Order_Pojo> recordList = new ArrayList<>();
    Configuration con = new Configuration().configure().addAnnotatedClass(Order_Pojo.class);
   	ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(con.getProperties()).buildServiceRegistry();
   	SessionFactory sf= con.buildSessionFactory(reg);
   	Session session = sf.openSession();
   	Transaction tx = session.beginTransaction();

    while (true) {
      ConsumerRecords<String, GenericRecord> records = consumer.poll(Duration.ofMillis(1000));

      for (ConsumerRecord<String, GenericRecord> record : records) {
        System.out.printf("Received message: key=%s, value=%s, partition=%d, offset=%d%n",
            record.key(), record.value(), record.partition(), record.offset());
        recordList.add((Order_Pojo) record.value());
      }
      for (Order_Pojo order : recordList) {
          session.save(order);
        }
      tx.commit();
    }
    
    
   
    
  }
}
