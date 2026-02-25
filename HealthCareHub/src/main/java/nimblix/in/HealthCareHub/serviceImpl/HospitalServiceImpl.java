package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.exception.UserNotFoundException;
import nimblix.in.HealthCareHub.repository.AppointmentRepository;
import nimblix.in.HealthCareHub.response.BedOccupancyReportResponse;
import nimblix.in.HealthCareHub.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final AppointmentRepository appointmentRepository;







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

    @Override
    public BedOccupancyReportResponse getHospitalOccupancyById(Long hospitalId) {
        if (hospitalId == null || hospitalId <= 0) {
            throw new IllegalArgumentException("Invalid hospital ID: " + hospitalId);
        }

        List<Object[]> results = appointmentRepository.getHospitalOccupancyById(hospitalId);

        if (results == null || results.isEmpty()) {
            throw new UserNotFoundException("Hospital occupancy data not found for ID: " + hospitalId);
        }

        Object[] row = results.get(0);
        return new BedOccupancyReportResponse(
                (Long) row[0],
                (String) row[1],
                (Double) row[2]
        );
    }
}



