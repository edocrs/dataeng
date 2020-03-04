package kafka.basic.exercise4;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.IntegerDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;


public class Exe3Consumer {
    public static void main(String[] args) {

        Properties props = new Properties();
        //TODO add List of Brokers
        //TODO set consumer ID
        //TODO add Deserializers for KEY
        //TODO add Deserializers for Message

        // TODO: Create a KafkaConsumer

        // TODO: Subscribe to the "evens" topic

        while (true) {

            //TODO Poll the topic
            ConsumerRecords<Integer, Integer> poll=null;

             poll.forEach(record ->
                    {
                        System.out.printf("offset = %s, key = %s, value = %s\n",
                                record.offset(), record.key(), record.value());
                    });
        }
    }

}
