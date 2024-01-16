package onions;

import ch.qos.logback.classic.Level;
import com.github.jesse.l2cache.spring.EnableL2Cache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableL2Cache
public class Main {

    public static void main(String[] args){
        SpringApplication.run(Main.class,args);
        System.out.println("启动成功");

    }
}
