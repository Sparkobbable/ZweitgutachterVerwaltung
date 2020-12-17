package util;

import java.util.logging.Logger;

/**
 * Supplies a {@link Logger} for the whole application featuring an fast and easy access
 */
public class Log {

	/**
	 * Logs the given message on loglevel warning.
	 * @param source	Source class of the log
	 * @param msg		The message to be logged
	 * @param args		The arguments to be featured in the message
	 */
	public static void warning(Object source, String msg, Object... args) {
		warning(source, String.format(msg, args));
	}

	/**
	 * Logs the given message on loglevel warning.
	 * @param source	Source class of the log
	 * @param msg		The message to be logged
	 */
	public static void warning(Object source, String msg) {
		Log.getLogger(source).warning(msg);
	}
	
	/**
	 * Logs the given message on loglevel info.
	 * @param source	Source class of the log
	 * @param msg		The message to be logged
	 * @param args		The arguments to be featured in the message
	 */
	public static void info(Object source, String msg, Object... args) {
		Log.getLogger(source).info(String.format(msg, args));
	}
	
	/**
	 * Logs the given message on loglevel severe.
	 * @param source	Source class of the log
	 * @param msg		The message to be logged
	 * @param args		The arguments to be featured in the message
	 */
	public static void severe(Object source, String msg, Object... args) {
		Log.getLogger(source).severe(String.format(msg, args));
	}

	private static Logger getLogger(Object source) {
		return Logger.getLogger(source.getClass().getName());
	}
}
