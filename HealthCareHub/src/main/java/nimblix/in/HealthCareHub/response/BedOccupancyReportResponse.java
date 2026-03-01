package nimblix.in.HealthCareHub.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BedOccupancyReportResponse {
    private Long hospitalId;
    private String hospitalName;
    private Double occupancyPercentage;
}