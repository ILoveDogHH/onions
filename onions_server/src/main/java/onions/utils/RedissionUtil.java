package onions.utils;

import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedissionUtil {

    @Autowired
    private RedissonClient redissonClient;

    // 设置键值对
    public <T> void setValue(String key, T value) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        bucket.set(value);
    }

    // 设置键值对，带有过期时间
    public <T> void setValue(String key, T value, long timeout, TimeUnit timeUnit) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        bucket.set(value, timeout, timeUnit);
    }

    // 获取键对应的值
    public <T> T getValue(String key) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    // 检查键是否存在
    public boolean keyExists(String key) {
        RBucket<Object> bucket = redissonClient.getBucket(key);
        return bucket.isExists();
    }

    // 设置Map的键值对
    public <T> void setMapValue(String mapKey, String key, T value) {
        RMap<String, T> map = redissonClient.getMap(mapKey);
        map.put(key, value);
    }

    // 获取Map中的值
    public <T> T getMapValue(String mapKey, String key) {
        RMap<String, T> map = redissonClient.getMap(mapKey);
        return map.get(key);
    }

    // 检查Map中是否存在键
    public boolean mapKeyExists(String mapKey, String key) {
        RMap<String, Object> map = redissonClient.getMap(mapKey);
        return map.containsKey(key);
    }

}
