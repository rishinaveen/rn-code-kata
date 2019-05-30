package com.rn.drivinghistoryfeature.writer;

import com.rn.drivinghistoryfeature.TestDataProvider;
import com.rn.drivinghistoryfeature.domain.DrivingHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.rn.drivinghistoryfeature.TestDataProvider.OUTPUT_TXT_1;
import static com.rn.drivinghistoryfeature.TestDataProvider.OUTPUT_TXT_6;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DrivingHistoryStringWriterTest
{
  private Set<DrivingHistory> setOfDrivingHistories;

  @BeforeEach
  void setup()
  {
    setOfDrivingHistories = TestDataProvider.SET_OF_DRIVING_HISTORIES;
  }

  @Test
  void writeDriverHistory()
  {
    IDrivingHistoryWriter writer = new DrivingHistoryStringWriter();
    assertEquals(
      OUTPUT_TXT_1,
      writer.writeDrivingHistory(setOfDrivingHistories)
    );
  }

  @Test
  void writeDriverHistoryWithNoInput()
  {
    IDrivingHistoryWriter writer = new DrivingHistoryStringWriter();
    assertEquals(
      OUTPUT_TXT_6,
      writer.writeDrivingHistory(null)
    );
  }
}
