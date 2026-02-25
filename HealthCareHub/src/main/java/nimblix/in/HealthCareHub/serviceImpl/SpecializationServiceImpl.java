package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.exception.UserNotFoundException;
import nimblix.in.HealthCareHub.repository.AppointmentRepository;
import nimblix.in.HealthCareHub.response.SpecializationPerformanceReportResponse;
import nimblix.in.HealthCareHub.service.SpecializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public List<SpecializationPerformanceReportResponse> getSpecializationPerformanceReport() {
        List<Object[]> results = appointmentRepository.getSpecializationPerformanceReport();

        if (results == null || results.isEmpty()) {
            throw new UserNotFoundException("Specialization performance data not found");
        }

        return results.stream()
                .map(row -> new SpecializationPerformanceReportResponse(
                        (Long) row[0],
                        (String) row[1],
                        (Long) row[2]
                ))
                .toList();
    }

    @Override
    public SpecializationPerformanceReportResponse getSpecializationPerformanceById(Long specializationId) {
        // ✅ Bad Request validation
        if (specializationId == null || specializationId <= 0) {
            throw new IllegalArgumentException("Invalid specialization ID: " + specializationId);
        }

        List<Object[]> results = appointmentRepository.getSpecializationPerformanceById(specializationId);

        if (results == null || results.isEmpty()) {
            throw new UserNotFoundException("Specialization performance data not found for ID: " + specializationId);
        }

        Object[] row = results.get(0);
        return new SpecializationPerformanceReportResponse(
                (Long) row[0],
                (String) row[1],
                (Long) row[2]
        );
    }
}