package onions;

import ch.qos.logback.classic.Level;
import onions.logger.JLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    public static void main(String[] args){
        JLogger.setLoggerLevel(Level.INFO);
        JLogger.debug("csdfa");
        JLogger.info("ccccc");
        JLogger.cron("dd");
        JLogger.dayLog("user", "act576", "acccccasfasfasfsafs");
        JLogger.dayLog("user", "act576", "acccccas23werfqrfasfasfsafs");
    }
}
