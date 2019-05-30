package com.rn;

import com.rn.drivinghistoryfeature.domain.DrivingHistory;
import com.rn.drivinghistoryfeature.processor.DrivingHistoryProcessor;
import com.rn.drivinghistoryfeature.processor.IDrivingHistoryProcessor;
import com.rn.drivinghistoryfeature.reader.DrivingHistoryFileReader;
import com.rn.drivinghistoryfeature.reader.IDrivingHistoryReader;
import com.rn.drivinghistoryfeature.writer.DrivingHistoryStringWriter;
import com.rn.drivinghistoryfeature.writer.IDrivingHistoryWriter;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
public class TaskExecutor
{
  private static final Logger LOGGER = LogManager.getLogger(TaskExecutor.class);

  private IDrivingHistoryReader    reader;
  private IDrivingHistoryProcessor processor;
  private IDrivingHistoryWriter    writer;

  public static void main(String[] args)
  {
    if (args == null || args.length == 0)
    {
      LOGGER.error("No inputs passed!");
      throw new IllegalArgumentException("No inputs passed. Exiting!");
    }

    TaskExecutor app = new TaskExecutor(new DrivingHistoryFileReader(), new DrivingHistoryProcessor(), new DrivingHistoryStringWriter());
    String result = app.execute(args[0]);

    LOGGER.debug("Processed driving history records from input: {}. " +
                   "Returning processed driving histories: \n{}", args[0], result);
    System.out.println("Processed driving history records from input: " + args[0]
                         + ". Returning processed driving histories: \n" + result);
    System.exit(0);
  }

  protected final String execute(String input)
  {
    try
    {
      Map<String, List<?>> map = this.reader.read(input);
      Set<DrivingHistory> histories = this.processor.processDriverHistory(map);
      return this.writer.writeDrivingHistory(histories);
    }
    catch (Exception e)
    {
      LOGGER.error("An error occurred while executing main task", e);
      throw new RuntimeException(e);
    }
  }
}