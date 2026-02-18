package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT YEAR(p.paymentDate), MONTH(p.paymentDate), SUM(p.amount), COUNT(p.id) " +
            "FROM Payment p " +
            "WHERE p.paymentDate IS NOT NULL " +
            "GROUP BY YEAR(p.paymentDate), MONTH(p.paymentDate)")
    List<Object[]> getMonthlyFinancialSummary();
}