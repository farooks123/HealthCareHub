package nimblix.in.HealthCareHub.serviceImpl;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.exception.SlotNotFoundException;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.exception.UserNotFoundException;
import nimblix.in.HealthCareHub.model.Doctor;
import nimblix.in.HealthCareHub.model.DoctorAvailability;
import nimblix.in.HealthCareHub.model.Hospital;
import nimblix.in.HealthCareHub.model.Specialization;
import nimblix.in.HealthCareHub.repository.DoctorAvailabilityRepository;
import nimblix.in.HealthCareHub.repository.DoctorRepository;
import nimblix.in.HealthCareHub.repository.HospitalRepository;
import nimblix.in.HealthCareHub.repository.SpecializationRepository;
import nimblix.in.HealthCareHub.request.DoctorAvailabilityRequest;
import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import nimblix.in.HealthCareHub.response.DoctorAvailabilityResponse;
import nimblix.in.HealthCareHub.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final SpecializationRepository specializationRepository;
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;

    // Validate slot date/time and prevent past date entries
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm");

    private static final ZoneId IST_ZONE = ZoneId.of("Asia/Kolkata");

    @Override
    public DoctorAvailabilityResponse addDoctorTimeSlot(Long doctorId, DoctorAvailabilityRequest request) {


        if (request.getAvailableDate() == null || request.getAvailableDate().trim().isEmpty())
            throw new RuntimeException("Available date cannot be null or empty");

        if (request.getStartTime() == null || request.getStartTime().trim().isEmpty())
            throw new RuntimeException("Start time cannot be null or empty");

        if (request.getEndTime() == null || request.getEndTime().trim().isEmpty())
            throw new RuntimeException("End time cannot be null or empty");

        if (request.getIsAvailable() == null)
            throw new RuntimeException("Availability status cannot be null");

       Doctor doctor= doctorRepository.findById(doctorId)
                .orElseThrow(() -> new UserNotFoundException(
                        "Doctor not found with id: " + doctorId));

        LocalDate slotDate = LocalDate.parse(
                request.getAvailableDate(), DATE_FORMATTER);

        LocalDate todayIST = LocalDate.now(IST_ZONE);
        if(slotDate.isBefore(todayIST)){
            throw new RuntimeException("Cannot add slot for past date");
        }
        LocalTime start = LocalTime.parse(request.getStartTime(), TIME_FORMATTER);
        LocalTime end = LocalTime.parse(request.getEndTime(), TIME_FORMATTER);

        if(!start.isBefore(end)){
            throw new RuntimeException("Start time must be before end time");
        }
//duplicate check
        boolean exists = doctorAvailabilityRepository
                .existsByDoctor_IdAndAvailableDateAndStartTime(
                        doctorId,
                        request.getAvailableDate(),
                        request.getStartTime()
                );

        if (exists) {
            throw new RuntimeException(
                    "Time slot already exists for doctor on " +
                            request.getAvailableDate() + " at " + request.getStartTime()
            );
        }
        boolean overlap = doctorAvailabilityRepository
                .existsOverlappingSlot(
                        doctorId,
                        request.getAvailableDate(),
                        request.getStartTime(),
                        request.getEndTime()
                );

        if (overlap) {
            throw new RuntimeException("Time slot overlaps with existing slot");
        }
        DoctorAvailability slot = DoctorAvailability.builder()
                .doctor(doctor)
                .availableDate(request.getAvailableDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .isAvailable(request.getIsAvailable())
                .build();

       DoctorAvailability saved = doctorAvailabilityRepository.save(slot);
        return doctorAvailabilityRepository.getSlotResponseById(saved.getId());
    }

    @Override
    public DoctorAvailabilityResponse updateDoctorTimeSlot(Long doctorId, Long slotId,
                                                   DoctorAvailabilityRequest request) {

        DoctorAvailability slot = doctorAvailabilityRepository.findById(slotId)
                .orElseThrow(() -> new SlotNotFoundException(
                        "Time slot not found with id: " + slotId
                ));
        if(!slot.getDoctor().getId().equals(doctorId)){
            throw new RuntimeException("slot does not belong to this doctor");
        }

        // Determine final values (existing or updated)
        String finalDate = request.getAvailableDate() != null
                ? request.getAvailableDate()
                : slot.getAvailableDate();

        String finalStart = request.getStartTime() != null
                ? request.getStartTime()
                : slot.getStartTime();

        String finalEnd = request.getEndTime() != null
                ? request.getEndTime()
                : slot.getEndTime();
        boolean finalAvailability = request.getIsAvailable() != null
                ? request.getIsAvailable()
                : slot.isAvailable();

        if (finalDate.equals(slot.getAvailableDate()) &&
                finalStart.equals(slot.getStartTime()) &&
                finalEnd.equals(slot.getEndTime()) &&
                ( finalAvailability == slot.isAvailable())) {
            throw new RuntimeException("No changes detected for update");
        }
        LocalDate parsedDate = LocalDate.parse(finalDate, DATE_FORMATTER);
        LocalDate todayIST = LocalDate.now(IST_ZONE);
        if (parsedDate.isBefore(todayIST)) {
            throw new RuntimeException("Cannot update slot to past date");
        }

        LocalTime start = LocalTime.parse(finalStart, TIME_FORMATTER);
        LocalTime end = LocalTime.parse(finalEnd, TIME_FORMATTER);

        if (!start.isBefore(end)) {
            throw new RuntimeException("Start time must be before end time");
        }
//  Duplicate check using final values
        boolean exists = doctorAvailabilityRepository
                .existsByDoctor_IdAndAvailableDateAndStartTimeAndIdNot(
                        doctorId,
                        finalDate,
                        finalStart,
                        slotId
                );

        if (exists) {
            throw new RuntimeException(
                    "Time slot already exists for doctor on " +
                            finalDate + " at " + finalStart
            );
        }
//  Overlap check using final values
        boolean overlap = doctorAvailabilityRepository
                .existsOverlappingSlotForUpdate(
                        doctorId,
                        finalDate,
                        finalStart,
                        finalEnd,
                        slotId
                );
        if (overlap) {
            throw new RuntimeException("Time slot overlaps with existing slot");
        }

        slot.setAvailableDate(finalDate);
        slot.setStartTime(finalStart);
        slot.setEndTime(finalEnd);
        slot.setAvailable(finalAvailability);

        DoctorAvailability saved = doctorAvailabilityRepository.save(slot);
        return doctorAvailabilityRepository.getSlotResponseById(saved.getId());
    }

    @Override
    public String registerDoctor(DoctorRegistrationRequest request) {
     try {
         // Check if email already exists
         if (doctorRepository.findByEmailId(request.getDoctorEmail()).isPresent()) {
             return "Doctor already exists with this email";
         }

         // Fetch Hospital
         Hospital hospital = hospitalRepository.findById(request.getHospitalId())
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
         doctor.setQualification(request.getQualification());
         doctor.setExperienceYears(request.getExperience());
         doctor.setDescription(request.getDescription());
         doctor.setHospital(hospital);

         // ✅ CORRECT WAY (Set Objects, not IDs)
         doctor.setHospital(hospital);
         doctor.setSpecialization(specialization);

         doctorRepository.save(doctor);

         return "Doctor Registered Successfully";
      }catch (UserNotFoundException e){
         return  "User not found";
     }
    }

    @Override
    public ResponseEntity<?> getDoctorDetails(Long doctorId, Long hospitalId) {

        Doctor doctor = doctorRepository
                .findByIdAndHospitalId(doctorId, hospitalId)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found in this hospital"));

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

        Specialization specialization = specializationRepository
                .findByName(request.getSpecializationName())
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


//    @Override
//    public String deleteDoctorDetails(Long doctorId) {
//
//        Doctor doctor = doctorRepository.findById(doctorId)
//                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));
//
//        doctorRepository.delete(doctor);
//
//        return "Doctor deleted successfully (Hard Delete)";
//    }


    @Override
    public String deleteDoctorDetails(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));

        doctor.setIsActive(HealthCareConstants.IN_ACTIVE);
        doctorRepository.save(doctor);

        return "Doctor deleted successfully (Hard Delete)";
    }



}

