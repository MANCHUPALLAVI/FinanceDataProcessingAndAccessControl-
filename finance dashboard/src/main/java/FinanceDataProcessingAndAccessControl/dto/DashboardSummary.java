package FinanceDataProcessingAndAccessControl.dto;

import java.util.List;
import java.util.Map;

import FinanceDataProcessingAndAccessControl.entity.FinancialRecord;

public class DashboardSummary 
{
	private Double totalIncome;
	private Double totalExpense;
	private Double netBalance;
	private Map<String, Double> categoryTotals;
	private List<FinancialRecord> recentTransactions;
	public Double getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(Double totalIncome) {
		this.totalIncome = totalIncome;
	}
	public Double getTotalExpense() {
		return totalExpense;
	}
	public void setTotalExpense(Double totalExpense) {
		this.totalExpense = totalExpense;
	}
	public Double getNetBalance() {
		return netBalance;
	}
	public void setNetBalance(Double netBalance) {
		this.netBalance = netBalance;
	}
	public Map<String, Double> getCategoryTotals() {
		return categoryTotals;
	}
	public void setCategoryTotals(Map<String, Double> categoryTotals) {
		this.categoryTotals = categoryTotals;
	}
	public List<FinancialRecord> getRecentTransactions() {
		return recentTransactions;
	}
	public void setRecentTransactions(List<FinancialRecord> recentTransactions) {
		this.recentTransactions = recentTransactions;
	}
}
