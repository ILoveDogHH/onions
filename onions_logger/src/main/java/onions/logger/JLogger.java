package onions.logger;

public class JLogger{



    public static void setLoggerLevel(){

    }

    /**
     * 输出 debug.log
     * @param s
     * @param objects
     */
    public static void debug(String s, Object... objects){
        LoggerManager.debug(s, objects);
    }

    /**
     * 输出 info.log
     * @param s
     * @param objects
     */
    public static void info(String s, Object... objects){
        LoggerManager.info(s, objects);
    }


    /**
     * 输出 cron.log
     * @param s
     * @param objects
     */
    public static void cron(String s, Object... objects){
        LoggerManager.cron(s, objects);
    }


    /**
     * 输出error.log
     * @param s
     * @param e
     */
    public static void error(String s, Throwable e){
        LoggerManager.error(s, e);
    }

}