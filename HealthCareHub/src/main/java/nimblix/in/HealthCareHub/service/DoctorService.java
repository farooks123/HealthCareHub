package nimblix.in.HealthCareHub.service;



import nimblix.in.HealthCareHub.response.DoctorPerformanceReportResponse;

import java.util.List;

public interface DoctorService {

    List<DoctorPerformanceReportResponse> getDoctorPerformanceReport();
    DoctorPerformanceReportResponse getDoctorPerformanceById(Long doctorId);


}
