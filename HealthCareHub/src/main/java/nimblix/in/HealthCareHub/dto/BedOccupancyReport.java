package nimblix.in.HealthCareHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BedOccupancyReport {
    private String hospitalName;
    private Integer totalBeds;
    private Long occupiedBeds;
    private Integer availableBeds;
}