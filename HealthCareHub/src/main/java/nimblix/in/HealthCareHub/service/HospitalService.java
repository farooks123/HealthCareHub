package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.request.HospitalRegistrationRequest;
import nimblix.in.HealthCareHub.response.BedOccupancyReportResponse;

import java.util.List;

public interface HospitalService {

    String registerHospital(HospitalRegistrationRequest request);

    List<BedOccupancyReportResponse> getHospitalOccupancyReport();

    BedOccupancyReportResponse getHospitalOccupancyById(Long hospitalId);
}