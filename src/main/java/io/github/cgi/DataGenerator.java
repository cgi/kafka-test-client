package io.github.cgi;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

@AllArgsConstructor
public class DataGenerator {

    private final String bootstrapServers;

    private final String topic;

    private final int msgCount;

    public void sendData() {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        List<PartitionInfo> infos = producer.partitionsFor(topic);
        for (int i = 0; i < msgCount; i++) {
            Future<RecordMetadata> future = producer.send(new ProducerRecord<>(topic, getPartition(infos, i), Integer.toString(i), Integer.toString(i)));
        }

        producer.close();
    }

    private int getPartition(List<PartitionInfo> infos, int num){
        int index = num % infos.size();
        return infos.get(index).partition();
    }
}
