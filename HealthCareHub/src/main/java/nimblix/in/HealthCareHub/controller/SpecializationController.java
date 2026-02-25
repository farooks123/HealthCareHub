package nimblix.in.HealthCareHub.controller;

import nimblix.in.HealthCareHub.response.SpecializationPerformanceReportResponse;
import nimblix.in.HealthCareHub.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specializations")
@RequiredArgsConstructor
public class SpecializationController {

    private final SpecializationService specializationService;

    // All specializations performance
    @GetMapping("/performance-report")
    public ResponseEntity<List<SpecializationPerformanceReportResponse>> getSpecializationPerformanceReport() {
        List<SpecializationPerformanceReportResponse> report = specializationService.getSpecializationPerformanceReport();
        return ResponseEntity.ok(report);
    }

    // Particular specialization performance by ID
    @GetMapping("/performance-report/{specializationId}")
    public ResponseEntity<SpecializationPerformanceReportResponse> getSpecializationPerformanceById(@PathVariable Long specializationId) {
        SpecializationPerformanceReportResponse report = specializationService.getSpecializationPerformanceById(specializationId);
        return ResponseEntity.ok(report);
    }
}