package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Long countByAppointmentDateTimeBetween(LocalDateTime start, LocalDateTime end);


    // Doctor performance report for all doctors
    @Query("SELECT a.doctor.id, a.doctor.name, COUNT(a), SUM(p.amount) " +
            "FROM Appointment a " +
            "JOIN Payment p ON p.appointment.id = a.id " +
            "WHERE a.status = 'COMPLETED' " +
            "GROUP BY a.doctor.id, a.doctor.name")
    List<Object[]> getDoctorPerformanceReport();

    // Doctor performance report for a specific doctor
    @Query("SELECT a.doctor.id, a.doctor.name, COUNT(a), SUM(p.amount) " +
            "FROM Appointment a " +
            "JOIN Payment p ON p.appointment.id = a.id " +
            "WHERE a.status = 'COMPLETED' AND a.doctor.id = :doctorId " +
            "GROUP BY a.doctor.id, a.doctor.name")
    List<Object[]> getDoctorPerformanceById(@Param("doctorId") Long doctorId);

    @Query("SELECT s.id, s.name, SUM(p.amount) " +
            "FROM Appointment a " +
            "JOIN a.doctor d " +
            "JOIN d.specialization s " +
            "JOIN Payment p ON p.appointment.id = a.id " +
            "WHERE a.status = 'COMPLETED' " +
            "GROUP BY s.id, s.name")
    List<Object[]> getSpecializationPerformanceReport();

    @Query("SELECT s.id, s.name, SUM(p.amount) " +
            "FROM Appointment a " +
            "JOIN a.doctor d " +
            "JOIN d.specialization s " +
            "JOIN Payment p ON p.appointment.id = a.id " +
            "WHERE a.status = 'COMPLETED' AND s.id = :specializationId " +
            "GROUP BY s.id, s.name")
    List<Object[]> getSpecializationPerformanceById(@Param("specializationId") Long specializationId);


    @Query("SELECT a.doctor.hospital.id, a.doctor.hospital.name, COUNT(a) " +
            "FROM Appointment a " +
            "WHERE a.status = 'COMPLETED' " +
            "GROUP BY a.doctor.hospital.id, a.doctor.hospital.name")
    List<Object[]> getHospitalPerformanceReport();

    @Query("SELECT a.doctor.hospital.id, a.doctor.hospital.name, COUNT(a) " +
            "FROM Appointment a " +
            "WHERE a.status = 'COMPLETED' AND a.doctor.hospital.id = :hospitalId " +
            "GROUP BY a.doctor.hospital.id, a.doctor.hospital.name")
    List<Object[]> getHospitalPerformanceById(Long hospitalId);


    @Query("SELECT h.id, h.name, " +
            "(CAST(COUNT(a) AS double) / h.totalBeds) * 100 " +
            "FROM Appointment a JOIN a.doctor d JOIN d.hospital h " +
            "WHERE a.status = 'COMPLETED' " +
            "GROUP BY h.id, h.name, h.totalBeds")
    List<Object[]> getHospitalOccupancyReport();

    @Query("SELECT h.id, h.name, " +
            "(CAST(COUNT(a) AS double) / h.totalBeds) * 100 " +
            "FROM Appointment a JOIN a.doctor d JOIN d.hospital h " +
            "WHERE a.status = 'COMPLETED' AND h.id = :hospitalId " +
            "GROUP BY h.id, h.name, h.totalBeds")
    List<Object[]> getHospitalOccupancyById(Long hospitalId);


    @Query("SELECT h.name, h.totalBeds, COUNT(a.id) " +
            "FROM Appointment a " +
            "JOIN a.doctor d " +
            "JOIN d.hospital h " +
            "WHERE a.status = 'COMPLETED' " +
            "GROUP BY h.name, h.totalBeds")
    List<Object[]> getBedOccupancyReport();



}