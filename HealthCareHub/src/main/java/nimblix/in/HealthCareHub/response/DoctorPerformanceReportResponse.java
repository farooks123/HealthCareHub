package nimblix.in.HealthCareHub.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorPerformanceReportResponse {
    private Long doctorId;
    private String doctorName;
    private Long patientCount;
    private Double totalRevenue;
}