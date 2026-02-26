package nimblix.in.HealthCareHub.repository;

import nimblix.in.HealthCareHub.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital,Long> {
    Optional<Hospital> findByName(String name);

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    @Query("SELECT h.name, h.totalBeds, COUNT(a.id) " +
            "FROM Hospital h " +
            "JOIN h.doctors d " +
            "JOIN d.appointments a " +
            "WHERE a.status = 'COMPLETED' " +
            "GROUP BY h.name, h.totalBeds")
    List<Object[]> getBedOccupancyReport();
}
