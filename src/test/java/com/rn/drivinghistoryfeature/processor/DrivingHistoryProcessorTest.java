package com.rn.drivinghistoryfeature.processor;

import com.rn.drivinghistoryfeature.TestDataProvider;
import com.rn.drivinghistoryfeature.domain.DrivingHistory;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DrivingHistoryProcessorTest
{
  private Map<String, List<?>> mapOfDriversAndTrips;
  private Set<DrivingHistory>  setOfDrivingHistories;

  @BeforeEach
  void setup()
  {
    mapOfDriversAndTrips = TestDataProvider.MAP_OF_DRIVER_AND_TRIP_OBJECTS;
    setOfDrivingHistories = TestDataProvider.SET_OF_DRIVING_HISTORIES;
  }

  @Test
  void processDriverHistory()
  {
    IDrivingHistoryProcessor processor = new DrivingHistoryProcessor();
    assertTrue(
      CollectionUtils.isEqualCollection(
        setOfDrivingHistories,
        processor.processDriverHistory(mapOfDriversAndTrips)
      )
    );
  }
}