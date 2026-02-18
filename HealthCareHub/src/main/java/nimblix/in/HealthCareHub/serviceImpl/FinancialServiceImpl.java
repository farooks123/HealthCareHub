package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.dto.MonthlyFinancialSummary;
import nimblix.in.HealthCareHub.repository.PaymentRepository;
import nimblix.in.HealthCareHub.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinancialServiceImpl implements FinancialService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<MonthlyFinancialSummary> getMonthlySummary() {
        List<Object[]> results = paymentRepository.getMonthlyFinancialSummary();
        List<MonthlyFinancialSummary> summaries = new ArrayList<>();

        for (Object[] row : results) {
            int year = row[0] != null ? ((Number) row[0]).intValue() : 0;
            int month = row[1] != null ? ((Number) row[1]).intValue() : 0;
            double totalRevenue = row[2] != null ? ((Number) row[2]).doubleValue() : 0.0;
            long totalBills = row[3] != null ? ((Number) row[3]).longValue() : 0L;

            summaries.add(new MonthlyFinancialSummary(year, month, totalRevenue, totalBills));
        }
        return summaries;
    }
}