package nimblix.in.HealthCareHub.request;

import lombok.Data;

@Data
public class DoctorPerformanceReportRequest {
    private String specialization; // optional filter
    private String startDate;      // optional filter
    private String endDate;        // optional filter
}