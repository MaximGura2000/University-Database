package src.helper;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class LoggerHelper {

  public void loggerSetting(Logger LOGGER) {
    // Create custom handler
    LOGGER.setUseParentHandlers(false);
    Handler handler = new Handler() {
      @Override
      public void publish(LogRecord record) {
        if (isLoggable(record)) {
          String message = getFormatter().format(record);
          System.out.print(message);
        }
      }

      @Override
      public void flush() {
      }

      @Override
      public void close() throws SecurityException {
      }
    };

    // Set custom log formatter on the handler
    handler.setFormatter(new Formatter() {
      @Override
      public String format(LogRecord record) {
        return record.getMessage() + System.lineSeparator();
      }
    });

    // Add custom handler to the LOGGER instance
    LOGGER.addHandler(handler);
  }

}
