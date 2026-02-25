package nimblix.in.HealthCareHub.request;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyVisitReportRequest {
    private LocalDate date; // required input for report
}