package onions.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerManager {
    private static final Logger debugLogger = LoggerFactory.getLogger("debugLogger");
    private static final Logger infoLogger = LoggerFactory.getLogger("infoLogger");
    private static final Logger errorLogger = LoggerFactory.getLogger("errorLogger");
    private static final Logger cronLogger = LoggerFactory.getLogger("cronLogger");



    /**
     * 输出到debug log
     * @param s
     * @param o
     */
    protected static void debug(String s, Object... o){
        debugLogger.debug(s, o);
    }


    /**
     * 输出到info log
     * @param s
     * @param o
     */
    protected static void info(String s, Object... o){
        infoLogger.info(s, o);
    }


    /**
     * 输出到error log
     * @param s
     * @param e
     */
    protected static void error(String s, Throwable e){
        errorLogger.error(s, e);
    }


    /**
     * 输出到cron log
     * @param s
     * @param o
     */
    protected static void cron(String s, Object... o){
        cronLogger.info(s, o);;
    }



}
