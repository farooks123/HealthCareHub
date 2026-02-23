package nimblix.in.HealthCareHub.controller;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import nimblix.in.HealthCareHub.response.DoctorProfileResponseDTO;
import nimblix.in.HealthCareHub.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("api/doctors")
@RequiredArgsConstructor
public class DoctorController {


    private final DoctorService doctorService;


    /*
Json object:
key and value pair

{
"name": "tejaswini",
"mobile number":"8937483454",
"date":"10-05-2026",
}

*/

    @PostMapping("/register")
    public String registerDoctor(@RequestBody DoctorRegistrationRequest doctorRegistrationRequest) {
        return doctorService.RegisterDoctor(doctorRegistrationRequest);
    }
    // GET /api/doctors/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getDoctorProfile(
            @PathVariable Long id) {

        DoctorProfileResponseDTO data = doctorService.getDoctorProfile(id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Doctor profile fetched successfully");
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }





}
