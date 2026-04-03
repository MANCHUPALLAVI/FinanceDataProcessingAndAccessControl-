package FinanceDataProcessingAndAccessControl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import FinanceDataProcessingAndAccessControl.dto.DashboardSummary;
import FinanceDataProcessingAndAccessControl.service.FinancialRecordService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private FinancialRecordService service;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public DashboardSummary getDashboard() {
        return service.getSummary();
    }
}