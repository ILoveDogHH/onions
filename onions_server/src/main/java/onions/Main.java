package onions;

import ch.qos.logback.classic.Level;
import onions.logger.JLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args){
        SpringApplication.run(Main.class,args);
        JLogger.setLoggerLevel(Level.INFO);
        System.out.println("启动成功");
    }
}
