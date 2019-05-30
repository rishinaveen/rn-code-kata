package com.rn.drivinghistoryfeature.reader;

import com.rn.drivinghistoryfeature.util.DrivingHistoryUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rn.drivinghistoryfeature.util.DrivingHistoryConstants.DRIVERS;
import static com.rn.drivinghistoryfeature.util.DrivingHistoryConstants.TRIPS;

public class DrivingHistoryFileReader implements IDrivingHistoryReader
{
  private static final Logger LOGGER = LogManager.getLogger(DrivingHistoryFileReader.class);

  @Override
  public Map<String, List<?>> read(String fileName) throws IOException, URISyntaxException
  {
    LOGGER.debug("Reading driving history commands from File: {}, located in classpath", fileName);
    try
    {
      Map<String, List<String>> drivingRecords = DrivingHistoryUtil.readFileAndParseToMap(fileName);

      // convert/map the driver/trip commands to Driver/Trip objects
      Map<String, List<?>> mapOfDriversAndTrips = new HashMap<>();
      mapOfDriversAndTrips.put(DRIVERS, DrivingHistoryUtil.commandsToDrivers(drivingRecords.get(DRIVERS)));
      mapOfDriversAndTrips.put(TRIPS, DrivingHistoryUtil.commandsToTrips(drivingRecords.get(TRIPS)));

      LOGGER.debug("Finished reading driving history from file: {}. Returning a map of Drivers and " +
                     "Trips: {}", fileName, mapOfDriversAndTrips);
      return mapOfDriversAndTrips;
    }
    catch (URISyntaxException | IOException ex)
    {
      LOGGER.fatal("Something wrong with the file: " + fileName + ". Please verify!", ex);
      throw ex;
    }
    finally
    {
      LOGGER.debug("DONE reading driving history from file: {}", fileName);
    }
  }
}
