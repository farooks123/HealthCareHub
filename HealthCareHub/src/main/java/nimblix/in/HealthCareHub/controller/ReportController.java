package nimblix.in.HealthCareHub.controller;

import nimblix.in.HealthCareHub.dto.BedOccupancyReport;
import nimblix.in.HealthCareHub.dto.DailyVisitReport;
import nimblix.in.HealthCareHub.dto.DepartmentRevenueReport;
import nimblix.in.HealthCareHub.dto.DoctorPerformanceReport;
import nimblix.in.HealthCareHub.service.AppointmentService;
import nimblix.in.HealthCareHub.service.BedService;
import nimblix.in.HealthCareHub.service.DepartmentService;
import nimblix.in.HealthCareHub.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private BedService bedService;





    // Endpoint: GET /api/reports/daily-visits
    @GetMapping("/daily-visits")
    public List<DailyVisitReport> getDailyVisits() {
        return appointmentService.getDailyVisitReport();
    }

    // Doctor Performance Report API
    @GetMapping("/doctor-performance")
    public List<DoctorPerformanceReport> getDoctorPerformance() {
        return doctorService.getDoctorPerformanceReport();
    }

    @GetMapping("/department-revenue")
    public List<DepartmentRevenueReport> getDepartmentRevenue() {
        return departmentService.getDepartmentRevenueReport();
    }

    // Endpoint for Bed Occupancy Report
    @GetMapping("/bed-occupancy")
    public List<BedOccupancyReport> getBedOccupancyReport() {
        return bedService.getBedOccupancyReport();
    }


}