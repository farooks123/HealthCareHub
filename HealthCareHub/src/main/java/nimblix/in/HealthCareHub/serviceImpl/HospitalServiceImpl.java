package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.exception.UserNotFoundException;
import nimblix.in.HealthCareHub.repository.AppointmentRepository;
import nimblix.in.HealthCareHub.response.BedOccupancyReportResponse;
import nimblix.in.HealthCareHub.service.HospitalService;
import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.repository.HospitalRepository;
import nimblix.in.HealthCareHub.request.HospitalRegistrationRequest;
import nimblix.in.HealthCareHub.service.HospitalService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final AppointmentRepository appointmentRepository;






    private final HospitalRepository hospitalRepository;

    @Override
    public List<BedOccupancyReportResponse> getHospitalOccupancyReport() {
        List<Object[]> results = appointmentRepository.getHospitalOccupancyReport();

        if (results == null || results.isEmpty()) {
            throw new UserNotFoundException("Hospital occupancy data not found");
        }

        return results.stream()
                .map(row -> new BedOccupancyReportResponse(
                        (Long) row[0],
                        (String) row[1],
                        (Double) row[2]
                ))
                .toList();
    }
    public String registerHospital(HospitalRegistrationRequest request) {

        // Check if hospital already exists
        if (hospitalRepository.findByName(request.getName()).isPresent()) {
            return "Hospital already exists";
    @Override
    public BedOccupancyReportResponse getHospitalOccupancyById(Long hospitalId) {
        if (hospitalId == null || hospitalId <= 0) {
            throw new IllegalArgumentException("Invalid hospital ID: " + hospitalId);
        }

        Hospital hospital = Hospital.builder()
                .name(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .phone(request.getPhone())
                .email(request.getEmail())
                .totalBeds(request.getTotalBeds())
                .build();
        List<Object[]> results = appointmentRepository.getHospitalOccupancyById(hospitalId);

        hospitalRepository.save(hospital);
        if (results == null || results.isEmpty()) {
            throw new UserNotFoundException("Hospital occupancy data not found for ID: " + hospitalId);
        }

        return "Hospital Registered Successfully";
        Object[] row = results.get(0);
        return new BedOccupancyReportResponse(
                (Long) row[0],
                (String) row[1],
                (Double) row[2]
        );
    }
}



