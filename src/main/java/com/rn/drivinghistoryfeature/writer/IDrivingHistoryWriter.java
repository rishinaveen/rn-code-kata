package com.rn.drivinghistoryfeature.writer;

import com.rn.drivinghistoryfeature.domain.DrivingHistory;

import java.util.Set;

/**
 * Interface for driving history writer
 */
public interface IDrivingHistoryWriter
{
  /**
   * Takes driving history records and generates a report with driver name,
   * total miles and average speed, sorted by most miles to least
   */
  String writeDrivingHistory(Set<DrivingHistory> drivingHistories);
}
