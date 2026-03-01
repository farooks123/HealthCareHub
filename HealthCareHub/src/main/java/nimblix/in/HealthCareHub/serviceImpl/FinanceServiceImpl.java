package nimblix.in.HealthCareHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.exception.UserNotFoundException;
import nimblix.in.HealthCareHub.repository.PaymentRepository;
import nimblix.in.HealthCareHub.response.MonthlyFinancialSummaryResponse;
import nimblix.in.HealthCareHub.service.FinanceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinanceServiceImpl implements FinanceService {

    private final PaymentRepository paymentRepository;


    /**
     * Monthly financial summary per hospital.
     */
    @Override
    public List<MonthlyFinancialSummaryResponse> getHospitalMonthlyFinancialSummary(int month, int year) {
        List<Object[]> results = paymentRepository.getHospitalMonthlyFinancialSummaryByMonth(month, year);

        if (results == null || results.isEmpty()) {
            throw new UserNotFoundException("No hospital financial summary found for " + month + "/" + year);
        }

        return results.stream()
                .map(row -> {
                    Double payments = row[4] != null ? ((Number) row[4]).doubleValue() : 0.0;
                    return new MonthlyFinancialSummaryResponse(
                            (Long) row[0],               // hospitalId
                            (String) row[1],             // hospitalName
                            row[3] + "-" + row[2],       // year-month
                            payments,                    // totalBilling approximated by payments
                            payments,                    // totalPayments
                            0.0                          // outstanding balance not tracked
                    );
                })
                .collect(Collectors.toList());
    }
}