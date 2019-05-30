package com.rn.drivinghistoryfeature.processor;

import com.rn.drivinghistoryfeature.domain.DrivingHistory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface for driving history processor
 */
public interface IDrivingHistoryProcessor
{
  /**
   * Performs below steps on a given list of drivers and trips
   * <p>
   * 1. Establish relationships between drivers and trips
   * 2. Calculate total miles driven and average avgMPH
   * 3. Aggregate above data into Driver History
   *
   * @param map a map of Drivers and Trips
   * @return A Set of driving history records
   */
  Set<DrivingHistory> processDriverHistory(Map<String, List<?>> map);
}
