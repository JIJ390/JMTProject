package edu.kh.jmt.common.util;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
		
	//StringRedisTemplate
	// - Redis에서 문자열 데이터를 쉽게 처리할 수 있는 클래스
	// - RedisConfig에서 Bean으로 등록된 RedisTemplate을 의존성 주입
	@Autowired
	private StringRedisTemplate redisTemplate;
	//Redis에서 키에 해당하는 값을 가져오는 메서드
	public String getValue(String key) {
	return redisTemplate.opsForValue().get(key);
	}
	//Redis에 키와 값을 저장하는 메서드, 값의 만료 시간도 설정
	public void setValue(String key, String value, long exprireDuration) {
	Duration duration = Duration.ofSeconds(exprireDuration);
	redisTemplate.opsForValue().set(key, value, duration);
	}
	//Redis에서 키에 해당하는 값을 삭제하는 메서드
	public void deleteValue(String key) {
	redisTemplate.delete(key);
	}
	//Redis에 키가 존재하는지 확인하는 메서드
	public boolean hasKey(String key) {
	return redisTemplate.hasKey(key);
}
}

