package com.vicky.Kafka;

public class Run {

	public static void main(String[] args) throws Exception {
		
		
		CsvToAvroConverter.main(args);
		
		TopicCreatorKafka.main(args);
		
		ProducerKafka.main(args);
		
		ConsumerKafka.main(args);
		
		Result2CSV.main(args);
		
		
	}

}
