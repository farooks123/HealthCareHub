package nimblix.in.HealthCareHub.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentRevenueResponse {
    private Long specializationId;
    private String specializationName;
    private Double totalRevenue;
}