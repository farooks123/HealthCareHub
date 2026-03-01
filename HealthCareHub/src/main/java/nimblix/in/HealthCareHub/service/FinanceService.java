package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.response.MonthlyFinancialSummaryResponse;

import java.util.List;

public interface FinanceService {


    List<MonthlyFinancialSummaryResponse> getHospitalMonthlyFinancialSummary(int month, int year);
}
