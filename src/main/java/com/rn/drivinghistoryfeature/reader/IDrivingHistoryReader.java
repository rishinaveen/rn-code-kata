package com.rn.drivinghistoryfeature.reader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * Interface for driving history reader
 */
public interface IDrivingHistoryReader
{

  /**
   * Takes an input text (a file name to read from (or) a string input) and convert the text (commands) into their Java representations
   *
   * ex:
   * input -
   * Driver Dan, Trip Dan 08:30 09:00 15
   * Driver Bob, Trip Bob 14:10 14:25 10
   *
   * output -
   * Drivers -> Driver(Dan), Driver(Bob)
   * Trips -> Trip(Dan), Trip(Bob)
   *
   * @param input
   * @return a map of Drivers and Trips
   */
  Map<String, List<?>> read(String input) throws IOException, URISyntaxException;
}
