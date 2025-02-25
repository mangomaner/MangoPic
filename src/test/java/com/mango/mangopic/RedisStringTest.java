package com.mango.mangopic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RedisStringTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedisStringOperations() {
        // ��ȡ��������
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();

        // Key �� Value
        String key = "testKey";
        String value = "testValue";

        // 1. ������������²���
        valueOps.set(key, value);
        String storedValue = valueOps.get(key);
        assertEquals(value, storedValue, "�洢��ֵ��Ԥ�ڲ�һ��");

        // 2. �����޸Ĳ���
        String updatedValue = "updatedValue";
        valueOps.set(key, updatedValue);
        storedValue = valueOps.get(key);
        assertEquals(updatedValue, storedValue, "���º��ֵ��Ԥ�ڲ�һ��");

        // 3. ���Բ�ѯ����
        storedValue = valueOps.get(key);
        assertNotNull(storedValue, "��ѯ��ֵΪ��");
        assertEquals(updatedValue, storedValue, "��ѯ��ֵ��Ԥ�ڲ�һ��");

        // 4. ����ɾ������
        stringRedisTemplate.delete(key);
        storedValue = valueOps.get(key);
        assertNull(storedValue, "ɾ�����ֵ��Ϊ��");
    }
}
