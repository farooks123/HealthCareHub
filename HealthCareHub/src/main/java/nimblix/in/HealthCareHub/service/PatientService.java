package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.response.DailyVisitReportResponse;
import java.time.LocalDate;

public interface PatientService {

    DailyVisitReportResponse getDailyVisits(LocalDate date);


}