package com.rn.drivinghistoryfeature;

import com.rn.drivinghistoryfeature.domain.Driver;
import com.rn.drivinghistoryfeature.domain.DrivingHistory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.rn.drivinghistoryfeature.util.DrivingHistoryUtil.commandToTrip;

public final class TestDataProvider
{
  public static final String INPUT_TXT_1 = "" +
                                             "Driver Dan\n" +
                                             "Driver Alex\n" +
                                             "Driver Bob\n" +
                                             "Trip Dan 07:15 07:45 17.3\n" +
                                             "Trip Dan 06:12 06:32 21.8\n" +
                                             "Trip Alex 12:01 13:16 42.0";

  public static final String OUTPUT_TXT_1 = "" +
                                              "Alex: 42 miles @ 34 mph\n" +
                                              "Dan: 39 miles @ 47 mph\n" +
                                              "Bob: 0 miles";

  public static final String OUTPUT_TXT_2 = "" +
                                              "Jacob: 73 miles @ 49 mph\n" +
                                              "Alex: 42 miles @ 34 mph\n" +
                                              "Dan: 39 miles @ 47 mph\n" +
                                              "Bob: 0 miles";

  public static final String OUTPUT_TXT_3 = "" +
                                              "Alex: 42 miles @ 34 mph\n" +
                                              "Dan: 39 miles @ 47 mph\n" +
                                              "Bob: 16 miles @ 63 mph\n" +
                                              "Jacob: 11 miles @ 22 mph";

  public static final String OUTPUT_TXT_6 = "No driving history records to write";

  /**
   * Returns a map of below data
   *
   * Driver Dan
   * Driver Alex
   * Driver Bob
   * Trip Dan 07:15 07:45 17.3
   * Trip Dan 06:12 06:32 21.8
   * Trip Alex 12:01 13:16 42.0
   */
  public static final Map<String, List<?>> MAP_OF_DRIVER_AND_TRIP_OBJECTS =
    new HashMap<String, List<?>>()
    {{
      put(
        "Drivers",
        Arrays.asList(
          Driver.of("Dan"),
          Driver.of("Alex"),
          Driver.of("Bob")
        )
      );
      put(
        "Trips",
        Arrays.asList(
          commandToTrip("Trip Dan 07:15 07:45 17.3"),
          commandToTrip("Trip Dan 06:12 06:32 21.8"),
          commandToTrip("Trip Alex 12:01 13:16 42.0")
        )
      );
    }};

  public static final Map<String, List<String>> MAP_OF_DRIVER_AND_TRIP_STRINGS =
      new HashMap<String, List<String>>()
      {{
        put(
            "Drivers",
            Arrays.asList(
                "Driver Dan",
                "Driver Alex",
                "Driver Bob"
            )
        );
        put(
            "Trips",
            Arrays.asList(
                "Trip Dan 07:15 07:45 17.3",
                "Trip Dan 06:12 06:32 21.8",
                "Trip Alex 12:01 13:16 42.0"
            )
        );
      }};

  public static final Set<DrivingHistory>  SET_OF_DRIVING_HISTORIES = Stream.of(
    DrivingHistory.of(
      Driver.of("Dan"),
      39,
      47,
      Arrays.asList(
        commandToTrip("Trip Dan 07:15 07:45 17.3"),
        commandToTrip("Trip Dan 06:12 06:32 21.8")
      )
    ),
    DrivingHistory.of(
      Driver.of("Alex"),
      42,
      34,
      Arrays.asList(
        commandToTrip("Trip Alex 12:01 13:16 42.0")
      )
    ),
    DrivingHistory.of(
      Driver.of("Bob"),
      0,
      0,
      new ArrayList<>()
    )
  ).collect(Collectors.toSet());

  private TestDataProvider() {}
}
