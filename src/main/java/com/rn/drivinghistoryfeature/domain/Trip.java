package com.rn.drivinghistoryfeature.domain;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalTime;

@Data(staticConstructor = "of")
public class Trip
{
  @NonNull
  private String    name;
  @NonNull
  private LocalTime start;
  @NonNull
  private LocalTime end;
  @NonNull
  private Float     miles;
}
