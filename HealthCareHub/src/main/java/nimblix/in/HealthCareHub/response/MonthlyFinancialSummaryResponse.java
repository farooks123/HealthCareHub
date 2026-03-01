package nimblix.in.HealthCareHub.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyFinancialSummaryResponse {
    private Long hospitalId;
    private String hospitalName;
    private String month; // e.g. "2026-02"
    private Double totalBilling;
    private Double totalPayments;
    private Double outstandingBalance;
}