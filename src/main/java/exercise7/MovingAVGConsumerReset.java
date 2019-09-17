package temperature.solutions.consumers;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import exercise5.model.TemperatureKey;
import exercise5.model.TemperatureValue;
import exercise5.deserialization.TemperatureKeyDeserializer;
import exercise5.deserialization.TemperatureValueDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class MovingAVGConsumerReset {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "avggroup");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, TemperatureKeyDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TemperatureValueDeserializer.class);

        // TODO: Create a new consumer, with the properties we've created above

        Consumer<TemperatureKey, TemperatureValue> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("temperature"));

        consumer.poll(1);


        while (true) {

            consumer.seekToBeginning(consumer.assignment());
            ConsumerRecords<TemperatureKey, TemperatureValue> records = consumer.poll(Duration.ofMillis(100));

            for (TopicPartition tp : consumer.assignment()) {

                List<ConsumerRecord<TemperatureKey, TemperatureValue>> records1 = records.records(tp);

                Map<String, List<ConsumerRecord<TemperatureKey, TemperatureValue>>> collect = records1.stream().collect(Collectors.groupingBy(o -> o.key().getLocation()));

                collect.forEach((key, value) -> {
                    Integer reduce = value.stream().map(e -> e.value().getValue())
                            .reduce(0, (integer, integer2) -> integer + integer2);

                    System.out.println(key.toString() + "  " + reduce / value.size());
                });
            }
        }
    }
}