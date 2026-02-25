package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.response.SpecializationPerformanceReportResponse;
import java.util.List;

public interface SpecializationService {
    List<SpecializationPerformanceReportResponse> getSpecializationPerformanceReport();
    SpecializationPerformanceReportResponse getSpecializationPerformanceById(Long specializationId);
}