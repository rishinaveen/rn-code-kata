package com.rn.drivinghistoryfeature.processor;

import com.rn.drivinghistoryfeature.domain.Driver;
import com.rn.drivinghistoryfeature.domain.DrivingHistory;
import com.rn.drivinghistoryfeature.domain.Trip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.rn.drivinghistoryfeature.util.DrivingHistoryConstants.DRIVERS;
import static com.rn.drivinghistoryfeature.util.DrivingHistoryConstants.TRIPS;
import static com.rn.drivinghistoryfeature.util.DrivingHistoryUtil.*;

public class DrivingHistoryProcessor implements IDrivingHistoryProcessor
{
  private static final Logger LOGGER = LogManager.getLogger(DrivingHistoryProcessor.class);

  @SuppressWarnings("unchecked")
  public final Set<DrivingHistory> processDriverHistory(final Map<String, List<?>> map)
  {
    List<Driver> drivers = (List<Driver>) map.get(DRIVERS);
    List<Trip> trips = (List<Trip>) map.get(TRIPS);
    LOGGER.debug("Processing driver history for given Drivers: {} and Trips: {}", drivers, trips);

    Set<DrivingHistory> drivingHistories = new HashSet<>();
    try
    {
      for (Driver driver : drivers)
      {
        List<Trip> driverTrips = filterValidTripsForDriver(driver.getName(), trips);
        drivingHistories.add(
          DrivingHistory.of(
            driver,
            Math.round(getTotalMiles(driverTrips)),
            Math.round(getAvgMPH(driverTrips)),
            driverTrips
          )
        );
      }
      return drivingHistories;
    }
    finally
    {
      LOGGER.debug("Finished processing driver history for given Drivers: {} and Trips: {}. DrivingHistories: {}", drivers, trips, drivingHistories);
    }
  }
}
