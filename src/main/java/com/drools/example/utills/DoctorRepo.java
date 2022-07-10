package com.drools.example.utills;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.random.RandomGenerator;

import javax.annotation.PostConstruct;

import com.drools.example.domains.Doctor;
import com.drools.example.domains.Hospital;
import com.drools.example.domains.Specialty;
import com.drools.example.enums.Gender;

import org.springframework.stereotype.Service;

@Service
public class DoctorRepo {

  private List<Doctor> doctors;
  private List<Specialty> specialties;
  private List<Gender> genders;
  private List<Hospital> hospitals;
  private List<LocalDate> doctorGraduatedDates;

  @PostConstruct
  private void postConduct() throws ParseException {
    Doctor[] doctors = new Doctor[1000];
    specialties = List.of(Specialty.builder().id(1L).name("heart").build(),
        Specialty.builder().id(2L).name("kidney").build(), Specialty.builder().id(2L).name("blood").build());
    hospitals = List.of(Hospital.builder().id(1L).name("Phoenix").build(),
        Hospital.builder().id(2L).name("Columpia").build());
    List<Boolean> insurerPanels = List.of(Boolean.TRUE, Boolean.FALSE);
    genders = List.of(Gender.MALE, Gender.FEMALE);
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    doctorGraduatedDates = List.of(LocalDate.parse("01-11-2000", dateFormat), LocalDate.parse("01-11-2013", dateFormat),
        LocalDate.parse("01-11-2020", dateFormat));

    for (int i = 0; i < 1000; i++) {
      int random = RandomGenerator.getDefault().nextInt(i + 1);
      doctors[i] = Doctor.builder().id(Long.valueOf(i)).doctorName(String.format("Doctor Name %d", i))
          .insurerPanel(insurerPanels.get(random % 2)).gender(genders.get(random % 2))
          .specialty(specialties.get(random % 3)).graduatedDate(doctorGraduatedDates.get(random % 3))
          .hospital(hospitals.get(random % 2)).build();
    }

    this.doctors = List.of(doctors);
  }

  public List<Doctor> getDoctors() {
    return doctors;
  }

  public List<Specialty> getSpecialties() {
    return specialties;
  }

  public List<Gender> getGenders() {
    return genders;
  }

  public List<Hospital> getHospitals() {
    return hospitals;
  }
}
