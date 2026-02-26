package nimblix.in.HealthCareHub.service;



import nimblix.in.HealthCareHub.response.DoctorPerformanceReportResponse;

import java.util.List;

public interface DoctorService {
    String registerDoctor(DoctorRegistrationRequest request);

    ResponseEntity<?> getDoctorDetails(Long doctorId, Long hospitalId);

    List<DoctorPerformanceReportResponse> getDoctorPerformanceReport();
    DoctorPerformanceReportResponse getDoctorPerformanceById(Long doctorId);


}
