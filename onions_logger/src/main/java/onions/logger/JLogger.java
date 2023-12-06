package onions.logger;

import ch.qos.logback.classic.Level;

public class JLogger{



    public static void setLoggerLevel(Level level){
        LoggerManager.setLevel(level);
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

    /**
     * fileName 文件夹
     * logName 文件名
     * msg msg
     * o 参数
     * @param fileName
     * @param logName
     * @param msg
     * @param o
     */
    public static void dayLog(String fileName, String logName, String msg, Object... o){
        String filePath = LoggerManager.getFilePath(fileName);
        LoggerManager.writeLoggerWithDay(logName, filePath, 0, msg, o);
    }


    /**
     * fileName 文件夹
     * logName 文件名
     * msg msg
     * o 参数
     * @param fileName
     * @param logName
     * @param msg
     * @param o
     */
    public static void dayLogDelay(String fileName, String logName, String msg, Object... o){
        String filePath = LoggerManager.getFilePath(fileName);
        LoggerManager.writeLoggerWithDay(logName, filePath, 15, msg, o);
    }

}