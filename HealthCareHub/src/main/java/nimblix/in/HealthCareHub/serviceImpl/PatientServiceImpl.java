package nimblix.in.HealthCareHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.exception.UserNotFoundException;
import nimblix.in.HealthCareHub.repository.AppointmentRepository;
import nimblix.in.HealthCareHub.response.DailyVisitReportResponse;
import nimblix.in.HealthCareHub.service.PatientService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public List<DailyVisitReportResponse> getDailyVisits(LocalDate date) {
        // Validate input
        if (date == null) {
            throw new IllegalArgumentException("Date parameter is required");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the future: " + date);
        }

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        // Query appointments between start and end of day
        Long count = appointmentRepository.countByAppointmentDateTimeBetween(startOfDay, endOfDay);





        if (count == null || count == 0) {
            throw new UserNotFoundException("No visits found for date: " + date);
        }

        // Return a DTO with the date and count
        return List.of(new DailyVisitReportResponse(date, count));
    }
}