package nimblix.in.HealthCareHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyFinancialSummary {
    private int year;
    private int month;
    private double totalRevenue;
    private long totalBills;
}