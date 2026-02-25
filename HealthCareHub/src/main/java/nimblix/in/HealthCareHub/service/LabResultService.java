package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.response.LabResultResponse;

import java.util.List;

public interface LabResultService {

    // Task 186 - Get lab result by ID
    LabResultResponse getLabResultById(Long resultId);

    // Get all lab results for a patient
    List<LabResultResponse> getLabResultsByPatient(Long patientId);
}