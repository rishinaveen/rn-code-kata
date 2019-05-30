package com.rn.drivinghistoryfeature.domain;

import lombok.Data;
import lombok.NonNull;

@Data(staticConstructor = "of")
public class Driver
{
  @NonNull
  private String name;
}
