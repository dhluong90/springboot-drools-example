package com.drools.example.dto;

import com.drools.example.domains.Doctor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorRuleFactRequest {

  private Doctor doctor;
  private SearchParam searchParam;
  private Integer priority = null;
}
