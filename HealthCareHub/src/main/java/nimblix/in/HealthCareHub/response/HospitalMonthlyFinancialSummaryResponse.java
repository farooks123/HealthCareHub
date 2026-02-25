package nimblix.in.HealthCareHub.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HospitalMonthlyFinancialSummaryResponse {
    private Long hospitalId;
    private String hospitalName;
    private int month;
    private int year;
    private Double totalPayments;
}