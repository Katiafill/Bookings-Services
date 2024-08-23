package ru.katiafill.bookings.flight;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@SpringBootApplication
@RefreshScope
@EnableFeignClients
@ComponentScan(basePackages = {
        "ru.katiafill.bookings.flight.*",
        "ru.katiafill.bookings.shared.*"
})
@EnableKafka
public class FlightServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightServiceApplication.class, args);
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setBasenames("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "bookings-flights-group");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaConsumerFactory<>(properties);
    }

}
