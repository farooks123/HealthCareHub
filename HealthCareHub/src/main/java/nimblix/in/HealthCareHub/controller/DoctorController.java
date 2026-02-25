package nimblix.in.HealthCareHub.controller;

import nimblix.in.HealthCareHub.response.DoctorPerformanceReportResponse;
import nimblix.in.HealthCareHub.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    // All doctors performance
    @GetMapping("/performance-report")
    public ResponseEntity<List<DoctorPerformanceReportResponse>> getDoctorPerformanceReport() {
        List<DoctorPerformanceReportResponse> report = doctorService.getDoctorPerformanceReport();
        return ResponseEntity.ok(report);
    }

    // Particular doctor performance by ID
    @GetMapping("/performance-report/{doctorId}")
    public ResponseEntity<DoctorPerformanceReportResponse> getDoctorPerformanceById(@PathVariable Long doctorId) {
        DoctorPerformanceReportResponse report = doctorService.getDoctorPerformanceById(doctorId);
        return ResponseEntity.ok(report);
    }
}