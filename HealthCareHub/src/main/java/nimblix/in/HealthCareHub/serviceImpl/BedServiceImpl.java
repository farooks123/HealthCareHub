package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.dto.BedOccupancyReport;
import nimblix.in.HealthCareHub.repository.HospitalRepository;
import nimblix.in.HealthCareHub.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BedServiceImpl implements BedService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public List<BedOccupancyReport> getBedOccupancyReport() {
        List<Object[]> results = hospitalRepository.getBedOccupancyReport();
        List<BedOccupancyReport> report = new ArrayList<>();

        for (Object[] row : results) {
            String hospitalName = (String) row[0];
            Long totalBeds = ((Number) row[1]).longValue();
            Long occupiedBeds = ((Number) row[2]).longValue();
            Long availableBeds = totalBeds - occupiedBeds;

            report.add(new BedOccupancyReport(
                    hospitalName,
                    totalBeds.intValue(),
                    occupiedBeds,
                    availableBeds.intValue()
            ));
        }

        return report;
    }
}