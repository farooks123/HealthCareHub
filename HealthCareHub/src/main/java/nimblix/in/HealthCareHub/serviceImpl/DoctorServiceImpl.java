package nimblix.in.HealthCareHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.exception.UserNotFoundException;
import nimblix.in.HealthCareHub.model.Doctor;
import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.model.Specialization;
import nimblix.in.HealthCareHub.repository.AppointmentRepository;
import nimblix.in.HealthCareHub.repository.DoctorRepository;
import nimblix.in.HealthCareHub.repository.HospitalRepository;
import nimblix.in.HealthCareHub.repository.SpecializationRepository;
import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import nimblix.in.HealthCareHub.response.DepartmentRevenueResponse;
import nimblix.in.HealthCareHub.response.DoctorPerformanceReportResponse;
import nimblix.in.HealthCareHub.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final SpecializationRepository specializationRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public String registerDoctor(DoctorRegistrationRequest request) {
        if (doctorRepository.findByEmailId(request.getDoctorEmail()).isPresent()) {
            return "Doctor already exists with this email";
        }

        Hospital hospital = hospitalRepository.findById(request.getHospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        Specialization specialization = specializationRepository.findByName(request.getSpecializationName())
                .orElseThrow(() -> new RuntimeException("Specialization not found"));

        Doctor doctor = new Doctor();
        doctor.setName(request.getDoctorName());
        doctor.setEmailId(request.getDoctorEmail());
        doctor.setPassword(request.getPassword());
        doctor.setPhone(request.getPhoneNo());
        doctor.setQualification(request.getQualification());
        doctor.setExperienceYears(request.getExperience());
        doctor.setDescription(request.getDescription());
        doctor.setHospital(hospital);
        doctor.setSpecialization(specialization);

        doctorRepository.save(doctor);
        return "Doctor Registered Successfully";
    }

    @Override
    public ResponseEntity<?> getDoctorDetails(Long doctorId, Long hospitalId) {
        Doctor doctor = doctorRepository.findByIdAndHospitalId(doctorId, hospitalId)
                .orElseThrow(() -> new RuntimeException("Doctor not found in this hospital"));
        return ResponseEntity.status(HttpStatus.OK).body(doctor);
    }

    @Override
    public String updateDoctorDetails(DoctorRegistrationRequest request) {
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        doctorRepository.findByEmailId(request.getDoctorEmail())
                .filter(existingDoctor -> !existingDoctor.getId().equals(doctor.getId()))
                .ifPresent(existingDoctor -> {
                    throw new RuntimeException("Email already used by another doctor");
                });

        Hospital hospital = hospitalRepository.findById(request.getHospitalId())
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        Specialization specialization = specializationRepository.findByName(request.getSpecializationName())
                .orElseThrow(() -> new RuntimeException("Specialization not found"));

        doctor.setName(request.getDoctorName());
        doctor.setEmailId(request.getDoctorEmail());
        doctor.setPassword(request.getPassword());
        doctor.setPhone(request.getPhoneNo());
        doctor.setQualification(request.getQualification());
        doctor.setExperienceYears(request.getExperience());
        doctor.setDescription(request.getDescription());
        doctor.setHospital(hospital);
        doctor.setSpecialization(specialization);

        doctorRepository.save(doctor);
        return "Doctor Updated Successfully";
    }

    @Override
    public String deleteDoctorDetails(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        // Soft delete (mark inactive)
        doctor.setIsActive(HealthCareConstants.IN_ACTIVE);
        doctorRepository.save(doctor);

        return "Doctor deleted successfully (Soft Delete)";
    }

    @Override
    public List<DoctorPerformanceReportResponse> getDoctorPerformanceReport() {
        List<Object[]> results = appointmentRepository.getDoctorPerformanceReport();

        if (results == null || results.isEmpty()) {
            throw new UserNotFoundException("Doctor performance data not found");
        }

        return results.stream()
                .map(row -> new DoctorPerformanceReportResponse(
                        (Long) row[0],       // doctorId
                        (String) row[1],     // doctorName
                        (Long) row[2],       // totalAppointments
                        row[3] != null ? (Double) row[3] : 0.0 // totalRevenue
                ))
                .collect(Collectors.toList());
    }

    @Override
    public DoctorPerformanceReportResponse getDoctorPerformanceById(Long doctorId) {
        if (doctorId == null || doctorId <= 0) {
            throw new IllegalArgumentException("Invalid doctor ID: " + doctorId);
        }

        List<Object[]> results = appointmentRepository.getDoctorPerformanceById(doctorId);

        if (results == null || results.isEmpty()) {
            throw new UserNotFoundException("Doctor performance data not found for ID: " + doctorId);
        }

        Object[] row = results.get(0);
        return new DoctorPerformanceReportResponse(
                (Long) row[0],       // doctorId
                (String) row[1],     // doctorName
                (Long) row[2],       // totalAppointments
                row[3] != null ? (Double) row[3] : 0.0 // totalRevenue
        );
    }



    @Override
    public List<DepartmentRevenueResponse> getRevenueBySpecialization() {
        List<Object[]> results = appointmentRepository.getRevenueBySpecialization();

        if (results == null || results.isEmpty()) {
            throw new UserNotFoundException("Revenue data not found for any specialization");
        }

        return results.stream()
                .map(row -> new DepartmentRevenueResponse(
                        (Long) row[0],       // specializationId
                        (String) row[1],     // specializationName
                        row[2] != null ? (Double) row[2] : 0.0 // totalRevenue
                ))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentRevenueResponse getRevenueBySpecializationId(Long specializationId) {
        if (specializationId == null || specializationId <= 0) {
            throw new IllegalArgumentException("Invalid specialization ID: " + specializationId);
        }

        List<Object[]> results = appointmentRepository.getRevenueBySpecializationId(specializationId);

        if (results == null || results.isEmpty()) {
            throw new UserNotFoundException("Revenue data not found for specialization ID: " + specializationId);
        }

        Object[] row = results.get(0);
        return new DepartmentRevenueResponse(
                (Long) row[0],
                (String) row[1],
                row[2] != null ? (Double) row[2] : 0.0
        );
    }
}

