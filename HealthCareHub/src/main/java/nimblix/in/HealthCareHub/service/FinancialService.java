package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.response.HospitalMonthlyFinancialSummaryResponse;
import nimblix.in.HealthCareHub.response.MonthlyFinancialSummaryResponse;
import java.util.List;

public interface FinancialService {
    List<MonthlyFinancialSummaryResponse> getMonthlyFinancialSummary();
    MonthlyFinancialSummaryResponse getMonthlyFinancialSummaryByMonth(int month, int year);

    List<HospitalMonthlyFinancialSummaryResponse> getHospitalMonthlyFinancialSummary();
    List<HospitalMonthlyFinancialSummaryResponse> getHospitalMonthlyFinancialSummaryByMonth(int month, int year);
}




