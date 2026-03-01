package nimblix.in.HealthCareHub.controller;

import nimblix.in.HealthCareHub.response.DailyVisitReportResponse;
import nimblix.in.HealthCareHub.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/daily-visits")
    public ResponseEntity<DailyVisitReportResponse> getDailyVisits(@RequestParam LocalDate date) {
        DailyVisitReportResponse response = patientService.getDailyVisits(date);
        return ResponseEntity.ok(response);
    }

}
