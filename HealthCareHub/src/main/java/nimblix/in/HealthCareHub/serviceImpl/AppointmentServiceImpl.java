package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.dto.DailyVisitReport;
import nimblix.in.HealthCareHub.repository.AppointmentRepository;
import nimblix.in.HealthCareHub.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    @Override
    public List<DailyVisitReport> getDailyVisitReport() {
        List<Object[]> results = repository.getDailyVisitCounts();
        List<DailyVisitReport> report = new ArrayList<>();

        for (Object[] row : results) {
            java.sql.Date sqlDate = (java.sql.Date) row[0];
            LocalDate date = sqlDate.toLocalDate();
            Long count = (Long) row[1];
            report.add(new DailyVisitReport(date, count));
        }
        return report;
    }
}