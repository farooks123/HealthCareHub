package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import nimblix.in.HealthCareHub.response.DepartmentRevenueResponse;
import org.springframework.http.ResponseEntity;



import nimblix.in.HealthCareHub.response.DoctorPerformanceReportResponse;

import java.util.List;

public interface DoctorService {

    String registerDoctor(DoctorRegistrationRequest request);

    ResponseEntity<?> getDoctorDetails(Long doctorId, Long hospitalId);

    String updateDoctorDetails(DoctorRegistrationRequest request);

    String deleteDoctorDetails(Long doctorId);

    List<DoctorPerformanceReportResponse> getDoctorPerformanceReport();

    DoctorPerformanceReportResponse getDoctorPerformanceById(Long doctorId);


    List<DepartmentRevenueResponse> getRevenueBySpecialization();
    DepartmentRevenueResponse getRevenueBySpecializationId(Long specializationId);
}

