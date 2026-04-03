package FinanceDataProcessingAndAccessControl.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import FinanceDataProcessingAndAccessControl.entity.FinancialRecord;
import FinanceDataProcessingAndAccessControl.enums.RecordType;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long>
{

    @Query("SELECT SUM(r.amount) FROM FinancialRecord r WHERE r.type='INCOME'")
    Double totalIncome();

    @Query("SELECT SUM(r.amount) FROM FinancialRecord r WHERE r.type='EXPENSE'")
    Double totalExpense();

    List<FinancialRecord> findByTypeAndCategory(RecordType type, String category);
    
    List<FinancialRecord> findByDateBetween(LocalDate start, LocalDate end);

    @Query("SELECT r.category, SUM(r.amount) FROM FinancialRecord r GROUP BY r.category")
    List<Object[]> categoryTotals();

    List<FinancialRecord> findTop5ByOrderByDateDesc();
    
}