package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT DATE(a.appointmentDateTime), COUNT(a) " +
            "FROM Appointment a " +
            "WHERE a.status = 'COMPLETED' " +
            "GROUP BY DATE(a.appointmentDateTime)")
    List<Object[]> getDailyVisitCounts();

    @Query("SELECT d.name, COUNT(a), SUM(p.amount) " +
            "FROM Appointment a " +
            "JOIN a.doctor d " +
            "JOIN Payment p ON p.appointment.id = a.id " +
            "WHERE a.status = 'COMPLETED' " +
            "GROUP BY d.name")
    List<Object[]> getDoctorPerformanceWithRevenue();

    @Query("SELECT s.name, SUM(p.amount) " +
            "FROM Appointment a " +
            "JOIN a.doctor d " +
            "JOIN d.specialization s " +
            "JOIN Payment p ON p.appointment.id = a.id " +
            "WHERE a.status = 'COMPLETED' " +
            "GROUP BY s.name")
    List<Object[]> getDepartmentRevenue();



}