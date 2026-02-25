package nimblix.in.HealthCareHub.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyFinancialSummaryResponse {
    private int month;
    private int year;
    private Double totalPayments;
}