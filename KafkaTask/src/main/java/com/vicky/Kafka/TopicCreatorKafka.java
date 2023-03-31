package com.vicky.Kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.Properties;

public class TopicCreatorKafka {
	public static void main(String[] args) {
		
		Properties props = new Properties();
		props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		AdminClient admin = AdminClient.create(props);

		String topicName = "orders";
		int numPartitions = 6;
		short replicationFactor = 3;

		NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);


		admin.createTopics(Collections.singleton(newTopic));
		System.out.println("Topic " + topicName + " created successfully");

		admin.close();
	}
}
