package nimblix.in.HealthCareHub.controller;

import nimblix.in.HealthCareHub.response.BedOccupancyReportResponse;
import nimblix.in.HealthCareHub.service.HospitalService;
import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.request.HospitalRegistrationRequest;
import nimblix.in.HealthCareHub.service.HospitalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/hospital")
@RequestMapping("/api/hospitals")
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    @PostMapping("/register")
    public String registerHospital(@RequestBody HospitalRegistrationRequest request) {
        return hospitalService.registerHospital(request);




    // All hospitals occupancy
    @GetMapping("/occupancy-report")
    public ResponseEntity<List<BedOccupancyReportResponse>> getHospitalOccupancyReport() {
        return ResponseEntity.ok(hospitalService.getHospitalOccupancyReport());
    }

}

    // Particular hospital occupancy
    @GetMapping("/occupancy-report/{hospitalId}")
    public ResponseEntity<BedOccupancyReportResponse> getHospitalOccupancyById(@PathVariable Long hospitalId) {
        return ResponseEntity.ok(hospitalService.getHospitalOccupancyById(hospitalId));
    }
