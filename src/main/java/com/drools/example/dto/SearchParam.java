package com.drools.example.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author : gandalf
 * @created : 2022-06-22
 **/

@Data
@Builder
public class SearchParam {

  private String gender;
  private Long specialtyID;
  private Boolean insurerPanel;
  private Long hospitalID;
  private String doctorLevel;

}
