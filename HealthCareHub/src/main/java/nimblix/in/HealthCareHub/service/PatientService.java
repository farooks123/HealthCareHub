package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.response.DailyVisitReportResponse;
import java.time.LocalDate;
import java.util.List;

public interface PatientService {

    List<DailyVisitReportResponse> getDailyVisits(LocalDate date);

}