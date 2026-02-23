package nimblix.in.HealthCareHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.exception.DoctorNotFoundException;
import nimblix.in.HealthCareHub.model.Doctor;
import nimblix.in.HealthCareHub.repository.DoctorRepository;
import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import nimblix.in.HealthCareHub.response.DoctorProfileResponseDTO;
import nimblix.in.HealthCareHub.service.DoctorService;
import org.springframework.stereotype.Service;

import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.model.Specialization;
import nimblix.in.HealthCareHub.repository.HospitalRepository;
import nimblix.in.HealthCareHub.repository.SpecializationRepository;


@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final SpecializationRepository specializationRepository;

    @Override
    public String RegisterDoctor(DoctorRegistrationRequest request) {

        Hospital hospital = hospitalRepository.findByName(request.getHospitalName())
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        Specialization specialization = specializationRepository.findByName(request.getSpecialization())
                .orElseThrow(() -> new RuntimeException("Specialization not found"));

        // Create Doctor
        Doctor doctor = new Doctor();

        doctor.setName(request.getDoctorName());
        doctor.setPhone(request.getPhoneNo());
        doctor.setEmailId(request.getDoctorEmail());
        doctor.setPassword(request.getPassword());

        // Set IDs (Correct way based on your entity)
        doctor.setHospitalId(hospital.getId());
        doctor.setSpecializationId(specialization.getId());

        doctorRepository.save(doctor);

        return "Doctor Registration Successful";
    }
    @Override
    public DoctorProfileResponseDTO getDoctorProfile(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException(
                        "Doctor not found with id: " + doctorId));

        Specialization specialization = null;
        if (doctor.getSpecializationId() != null) {
            specialization = specializationRepository.findById(doctor.getSpecializationId())
                    .orElse(null);
        }

        Hospital hospital = null;
        if (doctor.getHospitalId() != null) {
            hospital = hospitalRepository.findById(doctor.getHospitalId())
                    .orElse(null);
        }

        return mapToResponse(doctor, specialization, hospital);
    }

    private DoctorProfileResponseDTO mapToResponse(Doctor doctor,
                                                   Specialization specialization,
                                                   Hospital hospital) {
        DoctorProfileResponseDTO response = new DoctorProfileResponseDTO();


        response.setDoctorId(doctor.getId());
        response.setName(doctor.getName());
        response.setEmail(doctor.getEmailId());
        response.setPhone(doctor.getPhone());
        response.setQualification(doctor.getQualification());
        response.setExperienceYears(doctor.getExperienceYears());

        if (specialization != null) {
            response.setSpecializationId(specialization.getId());
            response.setSpecializationName(specialization.getName());
        }

        if (hospital != null) {
            response.setHospitalId(hospital.getId());
            response.setHospitalName(hospital.getName());
            response.setHospitalAddress(hospital.getAddress());
            response.setHospitalCity(hospital.getCity());
            response.setHospitalState(hospital.getState());
            response.setHospitalPhone(hospital.getPhone());
            response.setHospitalEmail(hospital.getEmail());
            response.setHospitalTotalBeds(hospital.getTotalBeds());
        }

        return response;
    }
}

