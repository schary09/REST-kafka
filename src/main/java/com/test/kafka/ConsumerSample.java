package com.test.kafka;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerSample {
	
	private List<String> topicNames;
	private Properties props;
	
	public List<String> getTopicNames() {
		return topicNames;
	}

	public void setTopicNames(List<String> topicNames) {
		this.topicNames = topicNames;
	}

	public ConsumerSample(List<String> topicNames) {
		this.topicNames = topicNames;
	}

	public void loadProperties()
	{
		InputStream istream = null;
		try {
			// file in target/classes
			istream = getClass().getClassLoader().getResourceAsStream("consumer.properties");
			props = new Properties();
			props.load(istream);
			istream.close();
		}catch(Exception io){
			io.printStackTrace();
		}
	}
	
	public void getData()
	{
		//loadProperties();
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", "test");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
		
		consumer.subscribe(topicNames);
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records){
				System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
			}
		}
	 
	}
	
	// This can be put in a separate file as consumer 1 or called here
	public static void main(String[] args){
		List<String> topicList = new ArrayList<String>();
		topicList.add("urltest");
		ConsumerSample cs = new ConsumerSample(topicList);
		cs.getData();
	}
}
