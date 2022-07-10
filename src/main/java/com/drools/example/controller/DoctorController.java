package com.drools.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.drools.example.domains.Doctor;
import com.drools.example.dto.DoctorRuleFactRequest;
import com.drools.example.dto.SearchParam;
import com.drools.example.utills.DoctorRepo;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/doctors")
public class DoctorController {

  @Autowired
  DoctorRepo doctorRepo;

  @Value("classpath:com/parkway/oreo/poc/doctor/doctorDecisionTable.xls")
  Resource resource;

  @GetMapping()
  public List<Doctor> getDoctors() {
    return doctorRepo.getDoctors();
  }

  @GetMapping("/search")
  public List<Doctor> searchDoctors(SearchParam searchParam) {
    KieContainer kieContainer = KieServices.Factory.get().newKieClasspathContainer();
    KieSession kieSession = kieContainer.newKieSession("DoctorTable");

    List<DoctorRuleFactRequest> requests = doctorRepo.getDoctors().stream()
        .map(d -> new DoctorRuleFactRequest(d, searchParam, null)).toList();

    requests.forEach(kieSession::insert);
    kieSession.fireAllRules();
    kieSession.dispose();
    return requests.stream().filter(i -> i.getPriority() != null).sorted((r1, r2) -> {
      return r2.getPriority().compareTo(r1.getPriority());
    }).map(DoctorRuleFactRequest::getDoctor).toList();
  }

  @GetMapping(value = "/ruleDownload", produces = "application/vnd.ms-excel")
  public ResponseEntity<Resource> downloadRuleFile() throws IOException {
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=doctorRuleFile.xls")
        .header(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel").body(resource);

  }

  @PostMapping("/ruleUpload")
  public void uploadRuleFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
    try (InputStream inputStream = multipartFile.getInputStream()) {
      Files.copy(inputStream, Path.of(resource.getFile().getPath()), StandardCopyOption.REPLACE_EXISTING);
    }
  }

}
