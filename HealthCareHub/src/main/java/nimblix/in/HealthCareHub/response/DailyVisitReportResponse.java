package nimblix.in.HealthCareHub.response;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyVisitReportResponse {
    private LocalDate date;
    private Long visitCount;
}