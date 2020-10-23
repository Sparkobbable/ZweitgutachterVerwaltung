package util;

import java.util.logging.Logger;

public class SystemUtils {
	private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public static void println(String msg) {
		System.out.println(msg);
	}
	
	public static void logInfo(String msg) {
		logger.info(msg);
	}
}
