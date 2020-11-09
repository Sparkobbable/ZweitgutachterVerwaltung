package util;

import java.util.logging.Logger;

public class Log {

	public static void warning(Object source, String msg, Object... args) {
		Log.getLogger(source).warning(String.format(msg, args));
	}
	
	public static void info(Object source, String msg, Object... args) {
		Log.getLogger(source).info(String.format(msg, args));
	}
	
	public static void severe(Object source, String msg, Object... args) {
		Log.getLogger(source).severe(String.format(msg, args));
	}

	private static Logger getLogger(Object source) {
		return Logger.getLogger(source.getClass().getName());
	}
}
