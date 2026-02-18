package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.dto.BedOccupancyReport;
import java.util.List;

public interface BedService {
    List<BedOccupancyReport> getBedOccupancyReport();
}