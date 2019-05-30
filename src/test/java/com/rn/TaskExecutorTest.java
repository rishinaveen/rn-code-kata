package com.rn;

import com.rn.drivinghistoryfeature.TestDataProvider;
import com.rn.drivinghistoryfeature.domain.DrivingHistory;
import com.rn.drivinghistoryfeature.processor.DrivingHistoryProcessor;
import com.rn.drivinghistoryfeature.processor.IDrivingHistoryProcessor;
import com.rn.drivinghistoryfeature.reader.DrivingHistoryFileReader;
import com.rn.drivinghistoryfeature.reader.IDrivingHistoryReader;
import com.rn.drivinghistoryfeature.writer.DrivingHistoryStringWriter;
import com.rn.drivinghistoryfeature.writer.IDrivingHistoryWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskExecutorTest
{
  @Test
  void main()
  {
    assertThrows(
      IllegalArgumentException.class,
      () -> TaskExecutor.main(null)
    );
  }


  @Test
  void executeWithInputs()
  {
    TaskExecutor taskExecutor = new TaskExecutor(new DrivingHistoryFileReader(), new DrivingHistoryProcessor(), new DrivingHistoryStringWriter());

    // passing input
    assertEquals(TestDataProvider.OUTPUT_TXT_1, taskExecutor.execute("input1-passing.txt"));

    // passing input
    assertEquals(TestDataProvider.OUTPUT_TXT_2, taskExecutor.execute("input2-passing.txt"));

    // passing input with blank lines
    assertEquals(TestDataProvider.OUTPUT_TXT_1, taskExecutor.execute("input3-passing.txt"));

    // failing input due to wrong command format
    assertThrows(Exception.class, () -> taskExecutor.execute("input4-failing.txt"));

    // passing input with trips with avgMph < 5mph or > 100mph
    assertEquals(TestDataProvider.OUTPUT_TXT_3, taskExecutor.execute("input5-passing.txt"));
  }

  @Test
  void executeWithMocks()
  {
    TaskExecutor taskExecutor = new TaskExecutor(new DummyReader(), new DummyProcessor(), new DummyWriter());
    assertEquals(TestDataProvider.OUTPUT_TXT_1, taskExecutor.execute("dummyInput"));
  }

  @Test
  void executeWithException()
  {
    TaskExecutor taskExecutor = new TaskExecutor(new DummyReaderThatThrowsException(), new DummyProcessor(), new DummyWriter());
    assertThrows(Exception.class, () -> taskExecutor.execute("dummyInput"));
  }

  class DummyReader implements IDrivingHistoryReader
  {
    @Override
    public Map<String, List<?>> read(String input) throws IOException, URISyntaxException
    {
      return TestDataProvider.MAP_OF_DRIVER_AND_TRIP_OBJECTS;
    }
  }

  class DummyReaderThatThrowsException implements IDrivingHistoryReader
  {
    @Override
    public Map<String, List<?>> read(String input) throws IOException, URISyntaxException
    {
      throw new RuntimeException("Throwing an exception");
    }
  }

  class DummyProcessor implements IDrivingHistoryProcessor
  {
    @Override
    public Set<DrivingHistory> processDriverHistory(Map<String, List<?>> map)
    {
      return TestDataProvider.SET_OF_DRIVING_HISTORIES;
    }
  }

  class DummyWriter implements IDrivingHistoryWriter
  {
    @Override
    public String writeDrivingHistory(Set<DrivingHistory> drivingHistories)
    {
      return TestDataProvider.OUTPUT_TXT_1;
    }
  }
}