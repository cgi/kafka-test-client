package io.github.cgi;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
public class DataConsumer {

    private final String bootstrapServers;

    private final String groupId;

    private final String topic;

    private final int msgCount;

    private final AtomicInteger msg = new AtomicInteger(0);
    public void getMessages(){
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", bootstrapServers);
        props.setProperty("group.id", groupId);
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        try( KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props) ) {
            consumer.subscribe( Collections.singletonList(topic) );

            while (msg.get() <= msgCount) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> rec : records) {
                    System.out.printf("rec = %s%n", rec.toString());
                    msg.incrementAndGet();
                }
            }
        }
    }
}
