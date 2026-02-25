package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.request.AdmitPatientRequestDTO;
import nimblix.in.HealthCareHub.response.AdmitPatientResponse;

public interface AdmissionService {

    AdmitPatientResponse admitPatient(AdmitPatientRequestDTO request);
}