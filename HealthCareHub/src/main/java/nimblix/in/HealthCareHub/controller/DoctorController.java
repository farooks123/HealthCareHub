package nimblix.in.HealthCareHub.controller;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import nimblix.in.HealthCareHub.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import nimblix.in.HealthCareHub.response.DoctorPerformanceReportResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/register")
    public String registerDoctor(@RequestBody DoctorRegistrationRequest request) {
        return doctorService.registerDoctor(request);
    }

    @GetMapping("/getDoctorDetails")
    public ResponseEntity<?> getDoctorDetails(@RequestParam Long doctorId,
                                              @RequestParam Long hospitalId) {
        return doctorService.getDoctorDetails(doctorId, hospitalId);
    }

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
