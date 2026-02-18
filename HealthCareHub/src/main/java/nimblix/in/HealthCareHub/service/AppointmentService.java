package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.dto.DailyVisitReport;
import java.util.List;

public interface AppointmentService {
    List<DailyVisitReport> getDailyVisitReport();
}