package com.rn.drivinghistoryfeature.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
public class DrivingHistory
{
  private final String smryTmplt1 = "%s: %s miles";
  private final String smryTmplt2 = " @ %s mph";

  @NonNull
  private Driver     driver;
  private Integer    miles  = 0;
  private Integer    avgMPH = 0;
  private List<Trip> trips;

  public final String toSummary()
  {
    StringBuilder builder = new StringBuilder()
                              .append(String.format(smryTmplt1, this.driver.getName(), this.getMiles()));

    if (this.getAvgMPH() != 0)
    {
      builder.append(String.format(smryTmplt2, this.getAvgMPH()));
    }

    return builder.toString();
  }
}
