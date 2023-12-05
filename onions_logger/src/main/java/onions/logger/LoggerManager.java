package onions.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LoggerManager {
    private static final Logger debugLogger = (Logger) LoggerFactory.getLogger("debugLogger");
    private static final Logger infoLogger = (Logger) LoggerFactory.getLogger("infoLogger");
    private static final Logger errorLogger = (Logger) LoggerFactory.getLogger("errorLogger");
    private static final Logger cronLogger = (Logger) LoggerFactory.getLogger("cronLogger");

    private static final String SYSTEM_PATH = System.getProperty ("user.dir");

    private static final String LOG_PATH = SYSTEM_PATH + File.separator + "log";
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





    /**
     * @param logName
     * @param filePath
     * @param dayRelay
     * @param s
     * @param o
     */
    protected static void writeLoggerWithDay(String logName, String filePath, int dayRelay,String s, Object... o){
        Logger logger = (Logger) LoggerFactory.getLogger(getLoggerName(logName));
        if(logger == null){
            logger = createLogger(logName, filePath, dayRelay);
        }
        logger = createLogger(logName, filePath, dayRelay);
        logger.info(s, o);
    }



    private synchronized static Logger createLogger(String logName, String filePath, int dayRelay){
        // 获取 LoggerContext
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        // 创建一个 RollingFileAppender
        RollingFileAppender fileAppender = new RollingFileAppender();
        fileAppender.setContext(context);
        fileAppender.setName(getAppenderName(logName));

        // 设置 TimeBasedRollingPolicy
        TimeBasedRollingPolicy rollingPolicy = new TimeBasedRollingPolicy();
        rollingPolicy.setContext(context);
        rollingPolicy.setParent(fileAppender);
        rollingPolicy.setFileNamePattern(getFilePattern(logName, filePath));
        if(dayRelay > 0){
            rollingPolicy.setMaxHistory(dayRelay); // 保留 day 天的日志
        }
        rollingPolicy.start();

        // 设置 Encoder
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n");
        encoder.start();

        // 配置 appender
        fileAppender.setEncoder(encoder);
        fileAppender.setRollingPolicy(rollingPolicy);
        fileAppender.start();

        // 获取或创建 Logger
        Logger dynamicLogger = context.getLogger(getLoggerName(logName));
        dynamicLogger.addAppender(fileAppender);
        dynamicLogger.setLevel(Level.INFO);
        return dynamicLogger;
    }



    protected static String getAppenderName(String logName){
        return logName + "Appender";
    }


    protected static String getLoggerName(String logName){
        return logName + "Logger";
    }

    protected static String getFilePattern(String logName, String filePath){
        return filePath + File.separator + logName+"-%d{yyyy-MM-dd}.log";
    }

    protected static String getFilePath(String fileName){
        return LOG_PATH +  File.separator + fileName;
    }


}
