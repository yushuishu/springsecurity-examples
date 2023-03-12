package com.shuishu.demo.security.common.config.redis;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author ：谁书-ss
 * @date ：2023-03-02 21:54
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：Redis配置
 * <p></p>
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        // 设置序列化
        //ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // JAVA_LANG_OBJECT: 当对象属性类型为Object时生效；
        // OBJECT_AND_NON_CONCRETE: 默认值， 当对象属性类型为Object或者非具体类型（抽象类和接口）时生效；
        // NON_CONCRETE_AND_ARRAYS: 同上, 另外所有的数组元素的类型都是非具体类型或者对象类型；
        // NON_FINAL: 对所有非final类型或者非final类型元素的数组。
        // EVERYTHING: 大多数情况下，这不是你想要的设置，因为它倾向于在所有地方添加Type id，即使在类型只能是声明的情况下(例如，如果一个属性的声明值类型是final 例如，long类型的属性(或包装器long)
        //objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator());
        //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // key
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        // value
        redisTemplate.setValueSerializer(RedisSerializer.json());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
