package com.rn.drivinghistoryfeature.util;

import com.rn.drivinghistoryfeature.domain.Driver;
import com.rn.drivinghistoryfeature.domain.Trip;
import com.rn.drivinghistoryfeature.exception.CommandParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rn.drivinghistoryfeature.util.DrivingHistoryConstants.*;

public final class DrivingHistoryUtil
{
  private static final Logger logger = LogManager.getLogger(DrivingHistoryUtil.class);

  private DrivingHistoryUtil() {}

  public static Driver commandToDriver(String command)
  {
    if (!commandValid(command)) { throw new CommandParseException("Driver Command is invalid. Command: " + command); }
    return Driver.of(command.split("\\s")[1]);
  }

  public static Trip commandToTrip(String command)
  {
    if (!commandValid(command)) { throw new CommandParseException("Trip Command is invalid. Command: " + command); }
    String[] frags = command.split("\\s");
    String name = frags[1];
    LocalTime startTime = checkTimeFormatAndParse(frags[2]);
    LocalTime endTime = checkTimeFormatAndParse(frags[3]);

    Float miles = new Float(frags[4]);
    return Trip.of(name, startTime, endTime, miles);
  }

  public static boolean commandValid(String command)
  {
    return command != null
             && command.length() > 0
             && command.matches("" +
                                  "^(Driver\\s[a-zA-Z]*)$" +
                                  "|" +
                                  "^(Trip\\s[a-zA-Z]*\\s((0[0-9]|1[0-9]|2[0-3]|[0-9]):[0-5][0-9]\\s){2}\\d*\\.?\\d*)$"
    );
  }

  private static LocalTime checkTimeFormatAndParse(String timeString)
  {
    String[] timeParts = timeString.split(":");
    return LocalTime.of(Integer.parseInt(timeParts[0]), Integer.parseInt(timeParts[1]));
  }

  public static List<Driver> commandsToDrivers(List<String> commands)
  {
    return commands != null && !commands.isEmpty()
             ? commands
                 .stream()
                 .map(DrivingHistoryUtil::commandToDriver)
                 .collect(Collectors.toList())
             : new ArrayList<>();
  }

  public static List<Trip> commandsToTrips(List<String> commands)
  {
    return commands != null && !commands.isEmpty()
             ? commands
                 .stream()
                 .map(DrivingHistoryUtil::commandToTrip)
                 .collect(Collectors.toList())
             : new ArrayList<>();
  }

  public static Map<String, List<String>> readFileAndParseToMap(String fileName) throws IOException, URISyntaxException
  {
    // get a reader for this file
    try (BufferedReader reader = Files.newBufferedReader(fileNameToPath(fileName)))
    {
      // try reading the lines and map the commands into two groups (drivers and trips)
      return reader
               .lines()
               .filter(line -> line.length() > 0)
               .collect(
                 Collectors.groupingByConcurrent(
                   line -> line.startsWith("Driver") ? "Drivers" : "Trips"
                 ));
    }
    catch (IOException ioEx)
    {
      logger.error("Something wrong with the file:" + fileName + ". Please verify", ioEx);
      throw ioEx;
    }
  }

  public static Path fileNameToPath(String fileName) throws FileNotFoundException, URISyntaxException
  {
    // getting the path to the file
    URL url = DrivingHistoryUtil.class.getClassLoader().getResource(fileName);
    if (url == null) { throw new FileNotFoundException(); }

    // getting the path object to file URI
    return Paths.get(url.toURI());
  }

  public static Float getTotalMiles(List<Trip> trips)
  {
    if (trips == null || trips.size() == 0) { return 0f; }
    Float totalMiles = 0f;
    for (Trip t : trips)
    {
      totalMiles = totalMiles + t.getMiles();
    }
    return totalMiles;
  }

  public static Float getTotalTimeInHours(List<Trip> trips)
  {
    if (trips == null || trips.size() == 0) { return 0f; }
    Float totalTime = 0f;
    for (Trip t : trips)
    {
      totalTime = totalTime + t.getStart().until(t.getEnd(), ChronoUnit.MINUTES);
    }
    return totalTime;
  }

  public static Float getAvgMPH(Trip trip)
  {
    return getAvgMPH(Collections.singletonList(trip));
  }

  public static Float getAvgMPH(List<Trip> trips)
  {
    if (trips != null && !trips.isEmpty())
    {
      Float totalMiles = getTotalMiles(trips);
      Float totalTimeInHours = getTotalTimeInHours(trips);
      return totalMiles != null
               && totalTimeInHours != null && totalTimeInHours != 0f
               ? totalMiles / totalTimeInHours * MINUTES_60 : 0f;
    }

    return 0f;
  }

  /**
   * This method is for filtering trips for a given driver and a list of trips.
   * Filter conditions are:
   * 1. Should belong to the driver passed in
   * 2. Trip speed is not less than 5 mph or greater than 100 mph.
   *
   * @param driverName name of the driver
   * @return a list of trips that applies to the driver in context
   */
  public static List<Trip> filterValidTripsForDriver(String driverName, List<Trip> trips)
  {
    return
      trips
        .parallelStream()
        .filter(DrivingHistoryUtil::validTrip)
        .filter(trip -> trip.getName().equals(driverName))
        .collect(Collectors.toList());
  }

  public static boolean validTrip(Trip trip)
  {
    Float avgMph = getAvgMPH(trip);
    return !(avgMph < FLOAT_5 || avgMph > FLOAT_100);
  }
}
