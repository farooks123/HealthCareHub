package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import nimblix.in.HealthCareHub.response.DoctorProfileResponseDTO;

public interface DoctorService {
    String RegisterDoctor(DoctorRegistrationRequest doctorRegistrationRequest);
    public DoctorProfileResponseDTO getDoctorProfile(Long doctorId);
}
