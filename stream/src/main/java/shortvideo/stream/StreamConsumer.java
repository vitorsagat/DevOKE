package shortvideo.stream;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class StreamConsumer {
	
	public void consume() {
			
		String authToken = "wB:>U+T[V6jCl)BcGV{u";
        String tenancyName = "oraclemetodista";
        String username = "vitor.t.barbosa@oracle.com";
        String streamingId = "ocid1.streampool.oc1.iad.amaaaaaaeun4owyavutq6svwas7elybxioxsw7ppn7fgdrmroydsofs5jm4a";
        String topicName = "TDC";
		
		
		/*
		 * String authToken = "wB:>U+T[V6jCl)BcGV{u"; String tenancyName =
		 * "oraclemetodista"; String username = "vitor.t.barbosa@oracle.com"; String
		 * streamingId =
		 * "ocid1.stream.oc1.us-sanjose-1.amaaaaaaeun4owya6hsfpv4v37cnhk4fviztqsprwbfe7juo7tjypwbqwuiq";
		 * String topicName = "TDC";
		 */
		
		Properties properties = new Properties();
        properties.put("bootstrap.servers", "https://cell-1.streaming.us-ashburn-1.oci.oraclecloud.com:9092");
        properties.put("security.protocol", "SASL_SSL");
        properties.put("sasl.mechanism", "PLAIN");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group-0");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        
        properties.put("sasl.jaas.config",
                "org.apache.kafka.common.security.plain.PlainLoginModule required username=\""
                        + tenancyName + "/"
                        + username + "/"
                        + streamingId + "\" "
                        + "password=\""
                        + authToken + "\";"
        );
        properties.put("max.partition.fetch.bytes", 1024 * 1024); 

        Consumer<Long, String> consumer = new KafkaConsumer<>(properties);
        try {
            consumer.subscribe(Collections.singletonList( topicName ) );

            while(true) {
                Duration duration = Duration.ofMillis(1000);
                ConsumerRecords<Long, String> consumerRecords = consumer.poll(duration);
                consumerRecords.forEach(record -> {
                    System.out.println("Record Key " + record.key());
                    System.out.println("Record value " + record.value());
                    System.out.println("Record partition " + record.partition());
                    System.out.println("Record offset " + record.offset());
                });
             
                consumer.commitAsync();
            }
        }
        catch(WakeupException e) {
          
        }
        finally {
            System.out.println("closing consumer");
            consumer.close();
        }

		
		
	}

}
