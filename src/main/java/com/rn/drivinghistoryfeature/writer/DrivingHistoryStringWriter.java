package com.rn.drivinghistoryfeature.writer;

import com.rn.drivinghistoryfeature.domain.DrivingHistory;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rn.drivinghistoryfeature.util.DrivingHistoryConstants.NO_DRIVING_HISTORY;

public class DrivingHistoryStringWriter implements IDrivingHistoryWriter
{
  @Override
  public String writeDrivingHistory(Set<DrivingHistory> drivingHistories)
  {
    if (drivingHistories == null || drivingHistories.size() == 0)
    {
      return NO_DRIVING_HISTORY;
    }

    return drivingHistories
             .parallelStream()
             .sorted(Comparator.comparingLong(DrivingHistory::getMiles).reversed())
             .map(history -> history.toSummary())
             .collect(Collectors.joining("\n"));
  }
}
