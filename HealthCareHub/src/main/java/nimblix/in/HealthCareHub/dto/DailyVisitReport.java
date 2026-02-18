package nimblix.in.HealthCareHub.dto;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DailyVisitReport {
    private final LocalDate date;
    private final Long totalVisits;
}