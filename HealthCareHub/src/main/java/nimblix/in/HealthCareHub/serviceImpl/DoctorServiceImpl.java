package nimblix.in.HealthCareHub.serviceImpl;

import nimblix.in.HealthCareHub.dto.DoctorPerformanceReport;
import nimblix.in.HealthCareHub.repository.AppointmentRepository;
import nimblix.in.HealthCareHub.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<DoctorPerformanceReport> getDoctorPerformanceReport() {
        List<Object[]> results = appointmentRepository.getDoctorPerformanceWithRevenue();
        List<DoctorPerformanceReport> report = new ArrayList<>();

        for (Object[] row : results) {
            String doctorName = (String) row[0];
            Long totalAppointments = (Long) row[1];
            Double totalRevenue = (Double) row[2];
            report.add(new DoctorPerformanceReport(doctorName, totalAppointments, totalRevenue));
        }
        return report;
    }
}