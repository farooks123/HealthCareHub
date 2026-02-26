package nimblix.in.HealthCareHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.exception.UserNotFoundException;
import nimblix.in.HealthCareHub.model.Doctor;
import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.model.Specialization;
import nimblix.in.HealthCareHub.repository.AppointmentRepository;
import nimblix.in.HealthCareHub.repository.DoctorRepository;
import nimblix.in.HealthCareHub.repository.HospitalRepository;
import nimblix.in.HealthCareHub.repository.SpecializationRepository;
import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import nimblix.in.HealthCareHub.response.DoctorPerformanceReportResponse;
import nimblix.in.HealthCareHub.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final SpecializationRepository specializationRepository;

    @Override
    public String registerDoctor(DoctorRegistrationRequest request) {
        // Check if email already exists
        if (doctorRepository.findByEmailId(request.getDoctorEmail()).isPresent()) {
            return "Doctor already exists with this email";
        }

        // Fetch Hospital
        Hospital hospital = hospitalRepository.findByName(request.getHospitalName())
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

        // Fetch Specialization
        Specialization specialization = specializationRepository.findByName(request.getSpecializationName())
                .orElseThrow(() -> new RuntimeException("Specialization not found"));

        // Create Doctor
        Doctor doctor = new Doctor();
        doctor.setName(request.getDoctorName());
        doctor.setEmailId(request.getDoctorEmail());
        doctor.setPassword(request.getPassword());
        doctor.setPhone(request.getPhoneNo());
        doctor.setHospital(hospital);
        doctor.setSpecialization(specialization);

        doctorRepository.save(doctor);

        return "Doctor Registered Successfully";
    }

    @Override
    public ResponseEntity<?> getDoctorDetails(Long doctorId, Long hospitalId) {
        // TODO: Implement properly (fetch doctor by ID and hospital, return DTO/ResponseEntity)
        return ResponseEntity.ok("Doctor details feature not yet implemented");
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
                        (Double) row[3]      // totalRevenue
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
                (Double) row[3]      // totalRevenue
        );
    }
}