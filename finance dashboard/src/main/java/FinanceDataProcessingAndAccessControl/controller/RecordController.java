package FinanceDataProcessingAndAccessControl.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import FinanceDataProcessingAndAccessControl.dto.RecordRequest;
import FinanceDataProcessingAndAccessControl.entity.FinancialRecord;
import FinanceDataProcessingAndAccessControl.enums.RecordType;
import FinanceDataProcessingAndAccessControl.service.FinancialRecordService;

@RestController
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private FinancialRecordService service;

    // CREATE
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public FinancialRecord create(@RequestBody RecordRequest req) {
        return service.create(req);
    }

    // READ
    @GetMapping
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public List<FinancialRecord> getAll() {
        return service.getAll();
    }

    // UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public FinancialRecord update(@PathVariable Long id, @RequestBody RecordRequest req) {
        return service.update(id, req);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        return service.delete(id);
    }

    // FILTER
    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ANALYST','ADMIN')")
    public List<FinancialRecord> filter(@RequestParam RecordType type,
                                        @RequestParam String category) {
        return service.filter(type, category);
    }
}