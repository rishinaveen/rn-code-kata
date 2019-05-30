package com.rn.drivinghistoryfeature.util;

import com.rn.drivinghistoryfeature.domain.Driver;
import com.rn.drivinghistoryfeature.domain.Trip;
import com.rn.drivinghistoryfeature.exception.CommandParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalTime;

import static com.rn.drivinghistoryfeature.TestDataProvider.MAP_OF_DRIVER_AND_TRIP_STRINGS;
import static org.junit.jupiter.api.Assertions.*;

class DrivingHistoryUtilTest
{

  @Test
  void convertCommandToDriver()
  {
    String command = "Driver Dan";
    assertEquals(
      Driver.of("Dan"),
      DrivingHistoryUtil.commandToDriver(command)
    );
  }

  @Test
  void convertCommandToTrip()
  {
    String command = "Trip Dan 12:30 13:00 20.5";
    assertEquals(
      Trip.of(
        "Dan",
        LocalTime.of(12, 30),
        LocalTime.of(13, 0),
        new Float("20.5")
      ),
      DrivingHistoryUtil.commandToTrip(command)
    );
  }

  @Test
  void convertCommandToTripWithException()
  {
    String command = "Trip Dan 12:30 13:00 20.5e";
    assertThrows(
      CommandParseException.class,
      () -> DrivingHistoryUtil.commandToTrip(command)
    );
  }

  @Test
  void invalidCommand()
  {
    String command = "BLAH";
    assertFalse(DrivingHistoryUtil.commandValid(command));
  }

  @Test
  void validDriverCommand()
  {
    String command = "Driver Dan";
    assertTrue(DrivingHistoryUtil.commandValid(command));
  }

  @Test
  void inValidDriverCommand()
  {
    String command = "Driver Dan Dan";
    assertFalse(DrivingHistoryUtil.commandValid(command));
  }

  @Test
  void validTripCommand()
  {
    String command = "Trip Dan 12:30 13:00 20.5";
    assertTrue(DrivingHistoryUtil.commandValid(command));
  }

  @Test
  void inValidTripCommand()
  {
    String command = "Trip Dan 12:30 13:00 20.5.5";
    assertFalse(DrivingHistoryUtil.commandValid(command));
  }

  @Test
  void readFileAndParseToMap() throws IOException, URISyntaxException {
    assertEquals(MAP_OF_DRIVER_AND_TRIP_STRINGS, DrivingHistoryUtil.readFileAndParseToMap("input.txt"));
  }

  @Test
  void nonExistentFileNameTest()
  {
    assertThrows(
      IOException.class,
      () -> DrivingHistoryUtil.readFileAndParseToMap("blah.txt")
    );
  }
}