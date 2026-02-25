package nimblix.in.HealthCareHub.controller;

import nimblix.in.HealthCareHub.response.HospitalMonthlyFinancialSummaryResponse;
import nimblix.in.HealthCareHub.response.MonthlyFinancialSummaryResponse;
import nimblix.in.HealthCareHub.service.FinancialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/financial")
@RequiredArgsConstructor
public class FinancialController {

    private final FinancialService financialService;

    // All months summary
    @GetMapping("/monthly-summary")
    public ResponseEntity<List<MonthlyFinancialSummaryResponse>> getMonthlyFinancialSummary() {
        return ResponseEntity.ok(financialService.getMonthlyFinancialSummary());
    }

    // Particular month summary
    @GetMapping("/monthly-summary/{year}/{month}")
    public ResponseEntity<MonthlyFinancialSummaryResponse> getMonthlyFinancialSummaryByMonth(
            @PathVariable int year, @PathVariable int month) {
        return ResponseEntity.ok(financialService.getMonthlyFinancialSummaryByMonth(month, year));
    }

    // ✅ Hospital-wise monthly summary
    @GetMapping("/hospitals/monthly-summary")
    public ResponseEntity<List<HospitalMonthlyFinancialSummaryResponse>> getHospitalMonthlyFinancialSummary() {
        return ResponseEntity.ok(financialService.getHospitalMonthlyFinancialSummary());
    }

    // ✅ Hospital-wise summary for a particular month
    @GetMapping("/hospitals/monthly-summary/{year}/{month}")
    public ResponseEntity<List<HospitalMonthlyFinancialSummaryResponse>> getHospitalMonthlyFinancialSummaryByMonth(
            @PathVariable int year, @PathVariable int month) {
        return ResponseEntity.ok(financialService.getHospitalMonthlyFinancialSummaryByMonth(month, year));
    }





}