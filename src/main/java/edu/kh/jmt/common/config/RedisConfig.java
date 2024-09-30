package edu.kh.jmt.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration // Spring의 설정 클래스를 나타냄
@PropertySource("classpath:config.properties") // 외부 프로퍼티 파일을 로드
public class RedisConfig {
  
  @Value("${spring.redis.host}") // 프로퍼티 파일에서 Redis 호스트 값을 주입
  private String redisHost;

  @Value("${spring.redis.port}") // 프로퍼티 파일에서 Redis 포트 값을 주입
  private int redisPort;

  @Bean // 해당 메서드에서 반환된 객체를 Bean으로 등록
  public RedisConnectionFactory redisConnectionFactory() {
    // LettuceConnectionFactory를 사용하여 Redis에 연결
    return new LettuceConnectionFactory(redisHost, redisPort);
  }  

  @Bean  // 해당 메서드에서 반환된 객체를 Bean으로 등록, <?, ?> 제네릭
  public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    // RedisTemplate을 생성하고 연결 팩토리를 설정
    RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    return template; // 설정된 RedisTemplate 반환
  }
}
