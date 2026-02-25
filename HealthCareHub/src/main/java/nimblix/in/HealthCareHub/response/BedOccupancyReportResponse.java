package nimblix.in.HealthCareHub.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BedOccupancyReportResponse {
    private Long hospitalId;
    private String hospitalName;
    private Double occupancyPercentage;
}