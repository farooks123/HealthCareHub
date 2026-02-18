package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.dto.DepartmentRevenueReport;
import nimblix.in.HealthCareHub.repository.AppointmentRepository;
import nimblix.in.HealthCareHub.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<DepartmentRevenueReport> getDepartmentRevenueReport() {
        List<Object[]> results = appointmentRepository.getDepartmentRevenue();
        List<DepartmentRevenueReport> report = new ArrayList<>();

        for (Object[] row : results) {
            String departmentName = (String) row[0];
            Double totalRevenue = (Double) row[1];
            report.add(new DepartmentRevenueReport(departmentName, totalRevenue));
        }
        return report;
    }
}