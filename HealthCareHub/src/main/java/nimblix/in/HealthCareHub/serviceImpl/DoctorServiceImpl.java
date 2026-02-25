package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.exception.UserNotFoundException;
import nimblix.in.HealthCareHub.repository.AppointmentRepository;
import nimblix.in.HealthCareHub.response.DoctorPerformanceReportResponse;
import nimblix.in.HealthCareHub.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public List<DoctorPerformanceReportResponse> getDoctorPerformanceReport() {
        List<Object[]> results = appointmentRepository.getDoctorPerformanceReport();

        if (results == null || results.isEmpty()) {
            throw new UserNotFoundException("Doctor performance data not found");
        }

        return results.stream()
                .map(row -> new DoctorPerformanceReportResponse(
                        (Long) row[0],
                        (String) row[1],
                        (Long) row[2]
                ))
                .collect(Collectors.toList());
    }

    @Override
    public DoctorPerformanceReportResponse getDoctorPerformanceById(Long doctorId) {
        // ✅ Bad Request validation
        if (doctorId == null || doctorId <= 0) {
            throw new IllegalArgumentException("Invalid doctor ID: " + doctorId);
        }

        List<Object[]> results = appointmentRepository.getDoctorPerformanceById(doctorId);

        if (results == null || results.isEmpty()) {
            throw new UserNotFoundException("Doctor performance data not found for ID: " + doctorId);
        }

        Object[] row = results.get(0);
        Long id = (Long) row[0];
        String name = (String) row[1];
        Long count = (Long) row[2];

        return new DoctorPerformanceReportResponse(id, name, count);
    }

}