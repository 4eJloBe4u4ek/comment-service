package backend.commentservice.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class CommentKafkaConsumerConfig {
    private final KafkaProperties kafkaProperties;

    @Bean
    public ConsumerFactory<Long, NewsDeletedEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "comment-service-group");

        JsonDeserializer<NewsDeletedEvent> deserializer = new JsonDeserializer<>(NewsDeletedEvent.class);
        deserializer.setTypeMapper(typeMapper());

        return new DefaultKafkaConsumerFactory<>(props, new LongDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, NewsDeletedEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, NewsDeletedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    @Bean
    public DefaultJackson2JavaTypeMapper typeMapper() {
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.addTrustedPackages("backend.commentservice.kafka");
        Map<String, Class<?>> mapping = new HashMap<>();
        mapping.put("backend.newsservice.kafka.NewsDeletedEvent", NewsDeletedEvent.class);
        typeMapper.setIdClassMapping(mapping);
        return typeMapper;
    }
}
