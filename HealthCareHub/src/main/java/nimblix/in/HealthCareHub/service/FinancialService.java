package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.dto.MonthlyFinancialSummary;
import java.util.List;

public interface FinancialService {
    List<MonthlyFinancialSummary> getMonthlySummary();
}