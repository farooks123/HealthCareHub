package nimblix.in.HealthCareHub.controller;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import nimblix.in.HealthCareHub.response.DepartmentRevenueResponse;
import nimblix.in.HealthCareHub.response.DoctorPerformanceReportResponse;
import nimblix.in.HealthCareHub.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    // Register a new doctor
    @PostMapping("/register")
    public ResponseEntity<String> registerDoctor(@RequestBody DoctorRegistrationRequest request) {
        String result = doctorService.registerDoctor(request);
        return ResponseEntity.ok(result);
    }

    // Get doctor details by ID and hospital
    @GetMapping("/details/{doctorId}/{hospitalId}")
    public ResponseEntity<?> getDoctorDetails(@PathVariable Long doctorId,
                                              @PathVariable Long hospitalId) {
        return doctorService.getDoctorDetails(doctorId, hospitalId);
    }

    // All doctors performance
    @GetMapping("/performance-report")
    public ResponseEntity<List<DoctorPerformanceReportResponse>> getDoctorPerformanceReport() {
        List<DoctorPerformanceReportResponse> report = doctorService.getDoctorPerformanceReport();
        return ResponseEntity.ok(report);
    }

    // Update doctor details
    @PutMapping("/update")
    public ResponseEntity<String> updateDoctorDetails(@RequestBody DoctorRegistrationRequest request) {
        String result = doctorService.updateDoctorDetails(request);
        return ResponseEntity.ok(result);
    }

    // Particular doctor performance by ID
    @GetMapping("/performance-report/{doctorId}")
    public ResponseEntity<DoctorPerformanceReportResponse> getDoctorPerformanceById(@PathVariable Long doctorId) {
        DoctorPerformanceReportResponse report = doctorService.getDoctorPerformanceById(doctorId);
        return ResponseEntity.ok(report);
    }

    // Delete doctor details
    @DeleteMapping("/delete/{doctorId}")
    public ResponseEntity<String> deleteDoctorDetails(@PathVariable Long doctorId) {
        String result = doctorService.deleteDoctorDetails(doctorId);
        return ResponseEntity.ok(result);
    }



    @GetMapping("/revenue-report")
    public ResponseEntity<List<DepartmentRevenueResponse>> getRevenueBySpecialization() {
        List<DepartmentRevenueResponse> report = doctorService.getRevenueBySpecialization();
        return ResponseEntity.ok(report);
    }

    // Revenue for a specific specialization
    @GetMapping("/revenue-report/{specializationId}")
    public ResponseEntity<DepartmentRevenueResponse> getRevenueBySpecializationId(@PathVariable Long specializationId) {
        DepartmentRevenueResponse report = doctorService.getRevenueBySpecializationId(specializationId);
        return ResponseEntity.ok(report);
    }




}