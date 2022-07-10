package com.drools.example.domains;

import java.time.LocalDate;

import com.drools.example.enums.Gender;

import lombok.Builder;
import lombok.Data;

/**
 * @author : gandalf
 * @created : 2022-06-22
 **/
@Data
@Builder
public class Doctor {

  private Long id;
  private String doctorName;
  private Gender gender;
  private boolean insurerPanel;
  private Specialty specialty;
  private Hospital hospital;
  private LocalDate graduatedDate;

}
