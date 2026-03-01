package nimblix.in.HealthCareHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.repository.AppointmentRepository;
import nimblix.in.HealthCareHub.response.DailyVisitReportResponse;
import nimblix.in.HealthCareHub.service.PatientService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public DailyVisitReportResponse getDailyVisits(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date parameter is required");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the future");
        }

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        Long count = appointmentRepository.countByAppointmentDateTimeBetween(startOfDay, endOfDay);

        // ✅ Improvement: return 0 instead of throwing exception
        if (count == null) {
            count = 0L;
        }

        return new DailyVisitReportResponse(date, count);
    }
}