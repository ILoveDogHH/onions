package onions;

import onions.logger.JLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    public static void main(String[] args){
        JLogger.error("c" , new Exception("cc"));
        JLogger.info("WErewewqewweerqwerr");
        JLogger.debug("cccccc");
    }
}
