package nimblix.in.HealthCareHub.controller;

import nimblix.in.HealthCareHub.dto.MonthlyFinancialSummary;
import nimblix.in.HealthCareHub.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class FinancialController {

    @Autowired
    private FinancialService financialService;

    @GetMapping("/monthly-financial-summary")
    public List<MonthlyFinancialSummary> getMonthlySummary() {
        return financialService.getMonthlySummary();
    }
}