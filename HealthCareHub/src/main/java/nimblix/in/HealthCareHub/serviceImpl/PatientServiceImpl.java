package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.exception.UserNotFoundException;
import nimblix.in.HealthCareHub.exception.PaymentException;
import nimblix.in.HealthCareHub.repository.AppointmentRepository;
import nimblix.in.HealthCareHub.response.DailyVisitReportResponse;
import nimblix.in.HealthCareHub.service.PatientService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<DailyVisitReportResponse> getDailyVisits(LocalDate date) {
        // ✅ Bad Request if date is null
        if (date == null) {
            throw new IllegalArgumentException("Date parameter is required");
        }

        // ✅ Optional: prevent querying future dates
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the future: " + date);
        }

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        Long count = appointmentRepository.countByAppointmentDateTimeBetween(startOfDay, endOfDay);

        if (count == null || count == 0) {
            throw new UserNotFoundException("No visits found for date: " + date);
        }
public class PatientServiceImpl {
}
