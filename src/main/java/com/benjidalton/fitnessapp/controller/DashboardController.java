package com.benjidalton.fitnessapp.controller;

import com.benjidalton.fitnessapp.dto.Responses.DashboardMetricsDto;
import com.benjidalton.fitnessapp.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(DashboardController.dashboardPath)
public class DashboardController {

    private final DashboardService dashboardService;
    public static final String dashboardPath = "dashboard";

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/")
    public DashboardMetricsDto getDashboardMetrics() {
        return dashboardService.getDashboardMetrics();
    }
}
