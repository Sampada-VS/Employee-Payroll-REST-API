package com.blz.employeeRESTapi;

import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollRESTService {
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	private List<EmployeePayrollData> employeePayrollList;

	public EmployeePayrollRESTService(List<EmployeePayrollData> employeePayrollList) {
		this.employeePayrollList = new ArrayList<>(employeePayrollList);
	}

	public EmployeePayrollRESTService() {
	}

	public long countEntries(IOService ioService) {
		return employeePayrollList.size();
	}

	public void addEmployeeToPayroll(EmployeePayrollData employeePayrollData, IOService ioService) {
		employeePayrollList.add(employeePayrollData);
	}

	public void updateEmployeesalary(String name, double salary, IOService ioService) {
		EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
		if (employeePayrollData != null)
			employeePayrollData.salary = salary;
	}

	public EmployeePayrollData getEmployeePayrollData(String name) {
		EmployeePayrollData employeePayrollData;
		employeePayrollData = this.employeePayrollList.stream().filter(dataItem -> dataItem.name.equals(name))
				.findFirst().orElse(null);
		return employeePayrollData;
	}
}
