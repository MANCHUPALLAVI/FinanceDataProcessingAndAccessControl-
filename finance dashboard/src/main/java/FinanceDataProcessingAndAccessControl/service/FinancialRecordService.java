package FinanceDataProcessingAndAccessControl.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import FinanceDataProcessingAndAccessControl.dto.DashboardSummary;
import FinanceDataProcessingAndAccessControl.dto.RecordRequest;
import FinanceDataProcessingAndAccessControl.entity.FinancialRecord;
import FinanceDataProcessingAndAccessControl.entity.User;
import FinanceDataProcessingAndAccessControl.enums.RecordType;
import FinanceDataProcessingAndAccessControl.repository.FinancialRecordRepository;
import FinanceDataProcessingAndAccessControl.repository.UserRepository;

@Service
public class FinancialRecordService {

    @Autowired
    private FinancialRecordRepository repo;
    
    @Autowired
    private UserRepository userRepo;
    
    // CREATE  
    public FinancialRecord create(RecordRequest req)
    {

        if (req.getAmount() <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isActive()) {
            throw new RuntimeException("User is inactive");
        }

        FinancialRecord r = new FinancialRecord();
        r.setAmount(req.getAmount());
        r.setType(req.getType());
        r.setCategory(req.getCategory());
        r.setDate(req.getDate());
        r.setDescription(req.getDescription());
        r.setCreatedBy(user);

        return repo.save(r);
    }

    // READ
    public List<FinancialRecord> getAll() {
        return repo.findAll();
    }

    // UPDATE
    public FinancialRecord update(Long id, RecordRequest req) {
        FinancialRecord r = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        r.setAmount(req.getAmount());
        r.setType(req.getType());
        r.setCategory(req.getCategory());
        r.setDate(req.getDate());
        r.setDescription(req.getDescription());

        return repo.save(r);
    }

    // DELETE
    public String delete(Long id) {
        FinancialRecord r = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        repo.delete(r);
        return "Record deleted successfully";
    }

    // FILTER
    public List<FinancialRecord> filter(RecordType type, String category) {
        return repo.findByTypeAndCategory(type, category);
    }

    // DATE FILTER (Bonus)
    public List<FinancialRecord> filterByDate(LocalDate start, LocalDate end) {
        return repo.findByDateBetween(start, end);
    }
    
    public DashboardSummary getSummary() {

        DashboardSummary d = new DashboardSummary();

        Double income = repo.totalIncome();
        Double expense = repo.totalExpense();

        //  Handle null values
        if (income == null) income = 0.0;
        if (expense == null) expense = 0.0;

        d.setTotalIncome(income);
        d.setTotalExpense(expense);
        d.setNetBalance(income - expense);

        // Category totals
        Map<String, Double> map = new HashMap<>();
        for (Object[] obj : repo.categoryTotals()) {
            map.put((String) obj[0], (Double) obj[1]);
        }
        d.setCategoryTotals(map);

        // Recent transactions
        d.setRecentTransactions(repo.findTop5ByOrderByDateDesc());

        return d;
    }
}