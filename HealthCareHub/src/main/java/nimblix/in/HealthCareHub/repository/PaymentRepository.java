package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT MONTH(p.paymentDate), YEAR(p.paymentDate), SUM(p.amount) " +
            "FROM Payment p " +
            "WHERE p.paymentStatus = 'PAID' " +   // ✅ Changed from SUCCESS to PAID
            "GROUP BY YEAR(p.paymentDate), MONTH(p.paymentDate)")
    List<Object[]> getMonthlyFinancialSummary();

    @Query("SELECT MONTH(p.paymentDate), YEAR(p.paymentDate), SUM(p.amount) " +
            "FROM Payment p " +
            "WHERE p.paymentStatus = 'PAID' AND MONTH(p.paymentDate) = :month AND YEAR(p.paymentDate) = :year " +
            "GROUP BY YEAR(p.paymentDate), MONTH(p.paymentDate)")
    List<Object[]> getMonthlyFinancialSummaryByMonth(int month, int year);

    @Query("SELECT h.id, h.name, MONTH(p.paymentDate), YEAR(p.paymentDate), SUM(p.amount) " +
            "FROM Payment p JOIN p.appointment a JOIN a.doctor d JOIN d.hospital h " +
            "WHERE p.paymentStatus = 'PAID' " +
            "GROUP BY h.id, h.name, YEAR(p.paymentDate), MONTH(p.paymentDate)")
    List<Object[]> getHospitalMonthlyFinancialSummary();

    @Query("SELECT h.id, h.name, MONTH(p.paymentDate), YEAR(p.paymentDate), SUM(p.amount) " +
            "FROM Payment p JOIN p.appointment a JOIN a.doctor d JOIN d.hospital h " +
            "WHERE p.paymentStatus = 'PAID' AND MONTH(p.paymentDate) = :month AND YEAR(p.paymentDate) = :year " +
            "GROUP BY h.id, h.name, YEAR(p.paymentDate), MONTH(p.paymentDate)")
    List<Object[]> getHospitalMonthlyFinancialSummaryByMonth(int month, int year);
}



