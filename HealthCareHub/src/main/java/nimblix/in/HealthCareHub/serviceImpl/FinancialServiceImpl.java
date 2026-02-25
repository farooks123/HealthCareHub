package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.exception.UserNotFoundException;
import nimblix.in.HealthCareHub.repository.PaymentRepository;
import nimblix.in.HealthCareHub.response.HospitalMonthlyFinancialSummaryResponse;
import nimblix.in.HealthCareHub.response.MonthlyFinancialSummaryResponse;
import nimblix.in.HealthCareHub.service.FinancialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialServiceImpl implements FinancialService {

    private final PaymentRepository paymentRepository;

    @Override
    public List<MonthlyFinancialSummaryResponse> getMonthlyFinancialSummary() {
        List<Object[]> results = paymentRepository.getMonthlyFinancialSummary();

        if (results == null || results.isEmpty()) {
            throw new UserNotFoundException("Monthly financial summary data not found");
        }

        return results.stream()
                .map(row -> new MonthlyFinancialSummaryResponse(
                        (Integer) row[0],  // month
                        (Integer) row[1],  // year
                        (Double) row[2]    // total payments
                ))
                .toList();
    }

    @Override
    public MonthlyFinancialSummaryResponse getMonthlyFinancialSummaryByMonth(int month, int year) {
        if (month <= 0 || month > 12 || year <= 0) {
            throw new IllegalArgumentException("Invalid month/year: " + month + "/" + year);
        }

        List<Object[]> results = paymentRepository.getMonthlyFinancialSummaryByMonth(month, year);

        if (results == null || results.isEmpty()) {
            throw new UserNotFoundException("Monthly financial summary data not found for " + month + "/" + year);
        }

        Object[] row = results.get(0);
        return new MonthlyFinancialSummaryResponse(
                (Integer) row[0],
                (Integer) row[1],
                (Double) row[2]
        );
    }

    @Override
    public List<HospitalMonthlyFinancialSummaryResponse> getHospitalMonthlyFinancialSummary() {
        List<Object[]> results = paymentRepository.getHospitalMonthlyFinancialSummary();

        if (results == null || results.isEmpty()) {
            throw new UserNotFoundException("Hospital monthly financial summary data not found");
        }

        return results.stream()
                .map(row -> new HospitalMonthlyFinancialSummaryResponse(
                        (Long) row[0],   // hospitalId
                        (String) row[1], // hospitalName
                        (Integer) row[2],// month
                        (Integer) row[3],// year
                        (Double) row[4]  // total payments
                ))
                .toList();
    }

    @Override
    public List<HospitalMonthlyFinancialSummaryResponse> getHospitalMonthlyFinancialSummaryByMonth(int month, int year) {
        if (month <= 0 || month > 12 || year <= 0) {
            throw new IllegalArgumentException("Invalid month/year: " + month + "/" + year);
        }

        List<Object[]> results = paymentRepository.getHospitalMonthlyFinancialSummaryByMonth(month, year);

        if (results == null || results.isEmpty()) {
            throw new UserNotFoundException("Hospital monthly financial summary data not found for " + month + "/" + year);
        }

        return results.stream()
                .map(row -> new HospitalMonthlyFinancialSummaryResponse(
                        (Long) row[0],
                        (String) row[1],
                        (Integer) row[2],
                        (Integer) row[3],
                        (Double) row[4]
                ))
                .toList();
    }
}



