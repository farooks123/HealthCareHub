package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.response.BedOccupancyReportResponse;

import java.util.List;

public interface HospitalService {

    List<BedOccupancyReportResponse> getHospitalOccupancyReport();
    BedOccupancyReportResponse getHospitalOccupancyById(Long hospitalId);

}