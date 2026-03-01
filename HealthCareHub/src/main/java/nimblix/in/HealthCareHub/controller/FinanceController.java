package nimblix.in.HealthCareHub.controller;

import nimblix.in.HealthCareHub.response.MonthlyFinancialSummaryResponse;
import nimblix.in.HealthCareHub.service.FinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
public class FinanceController {

    private final FinanceService financeService;



    // Monthly summary per hospital
    @GetMapping("/monthly-summary/hospitals")
    public ResponseEntity<List<MonthlyFinancialSummaryResponse>> getHospitalMonthlySummary(
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(financeService.getHospitalMonthlyFinancialSummary(month, year));
    }
}