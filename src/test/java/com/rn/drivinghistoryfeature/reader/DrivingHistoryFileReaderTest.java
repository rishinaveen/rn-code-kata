package com.rn.drivinghistoryfeature.reader;

import com.rn.drivinghistoryfeature.TestDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DrivingHistoryFileReaderTest
{
  private Map<String, List<?>> mapOfDriversAndTrips;

  @BeforeEach
  void setUp()
  {
    mapOfDriversAndTrips = TestDataProvider.MAP_OF_DRIVER_AND_TRIP_OBJECTS;
  }

  @Test
  void readInput() throws URISyntaxException, IOException
  {
    IDrivingHistoryReader reader = new DrivingHistoryFileReader();
    assertEquals(
      mapOfDriversAndTrips,
      reader.read("input1-passing.txt")
    );
  }

  @Test
  void readInputWithIOException()
  {
    IDrivingHistoryReader reader = new DrivingHistoryFileReader();
    assertThrows(
      FileNotFoundException.class,
      () -> reader.read("blah.txt")
    );
  }
}