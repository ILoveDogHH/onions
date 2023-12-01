package onions.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RedissionConfig {


    @Bean(destroyMethod="shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
         config.useClusterServers()
             .addNodeAddress("redis://8.222.174.54:7001", "redis://8.222.174.54:7002");
         config.setCodec(new JsonJacksonCodec());
        RedissonClient client = Redisson.create(config);
        System.out.println("redis 集群连接成功");
        return client;
    }
}
