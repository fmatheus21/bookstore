package com.fmatheus.app.config;


import org.springframework.core.annotation.AliasFor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@KafkaListener
public @interface KafkaConsumerCustomListener {

    @AliasFor(annotation = KafkaListener.class, attribute = "topics")
    String[] topics() default "${api.kafka.topic}";

    @AliasFor(annotation = KafkaListener.class, attribute = "containerFactory")
    String containerFactor() default "listenerContainerFactory";

    @AliasFor(annotation = KafkaListener.class, attribute = "groupId")
    String groupId() default "group-1";

    @AliasFor(annotation = KafkaListener.class, attribute = "topicPartitions")
    TopicPartition[] topicPartitions() default {@TopicPartition(topic = "${api.kafka.topic}",
            partitionOffsets = {@PartitionOffset(partition = "0", initialOffset = "0"), @PartitionOffset(partition = "1", initialOffset = "0")})};
}
