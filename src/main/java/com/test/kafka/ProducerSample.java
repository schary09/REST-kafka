package com.test.kafka;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;

public class ProducerSample {
	
	private String topicName;
	private Integer partition;
	private Properties props;
	Producer<String, String> producer;

	
	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public Integer getPartition() {
		return partition;
	}

	public void setPartition(Integer partition) {
		this.partition = partition;
	}
	
	public void loadProperties()
	{
		InputStream istream = null;
		try {
			// file in target/classes
			istream = getClass().getClassLoader().getResourceAsStream("config.properties");
			props = new Properties();
			props.load(istream);
			istream.close();
		}catch(Exception io){
			io.printStackTrace();
		}
	}
	
	public ProducerSample(String topicName, Integer partition)
	{
		this.topicName = topicName;
		this.partition = partition;
		loadProperties();
		/*Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
				*/
		producer = new KafkaProducer<String, String>(props);
	}
	
	public void sendData(String key, String value) {
		ProducerRecord record = new ProducerRecord(topicName,partition, key, value);
		producer.send(record);		
	}
	
	public void closeProducer()
	{
		producer.close();
	}
	
	public static void main(String[] args){
		String msg="testing";
		String key = "searchkey";
    	ProducerSample ps = new ProducerSample("urltest",0);
    	ps.sendData(key, msg);
    	ps.closeProducer();
    	System.out.println("Producer sent data to topic:" + "key="+key + " msg=" + msg);
 
	}	
}
