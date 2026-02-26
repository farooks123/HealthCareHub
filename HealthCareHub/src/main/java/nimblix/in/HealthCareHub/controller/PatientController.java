package nimblix.in.HealthCareHub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import nimblix.in.HealthCareHub.response.DailyVisitReportResponse;
import nimblix.in.HealthCareHub.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/patient")
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/daily-visits")
    public ResponseEntity<List<DailyVisitReportResponse>> getDailyVisits(
            @RequestParam LocalDate date) {
        List<DailyVisitReportResponse> response = patientService.getDailyVisits(date);
        return ResponseEntity.ok(response); // 200 OK
    }
}
