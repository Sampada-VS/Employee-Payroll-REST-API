package com.blz.employeeRESTapi;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static com.blz.employeeRESTapi.EmployeePayrollRESTService.IOService.REST_IO;

public class EmployeePayrollRESTServiceTest {

	static EmployeePayrollRESTService employeePayrollRESTService;

	@BeforeClass
	public static void createObj() {
		employeePayrollRESTService = new EmployeePayrollRESTService();
	}

	@AfterClass
	public static void nullObj() {
		employeePayrollRESTService = null;
	}

	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	private static EmployeePayrollData[] getEmployeeList() {
		Response response = RestAssured.get("/employee_payroll");
		System.out.println("Employee Payroll entries in JsonServer :\n" + response.asString());
		EmployeePayrollData[] arrayOfEmployee = new Gson().fromJson(response.asString(), EmployeePayrollData[].class);
		return arrayOfEmployee;
	}

	private Response addEmployeeToJsonServer(EmployeePayrollData employeePayrollData) {
		String employeeJson = new Gson().toJson(employeePayrollData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(employeeJson);
		return request.post("/employee_payroll");
	}

//	@Test
//	public void givenNewEmployee_WhenAdded_ShouldMatchResponseCode201AndCount() {
//		EmployeePayrollData[] arrayOfEmployee = getEmployeeList();
//		employeePayrollRESTService = new EmployeePayrollRESTService(Arrays.asList(arrayOfEmployee));
//
//		EmployeePayrollData employeePayrollData = new EmployeePayrollData(0, "Mark", "M", 3000000.00, LocalDate.now());
//
//		Response response = addEmployeeToJsonServer(employeePayrollData);
//		int statusCode = response.getStatusCode();
//		assertEquals(201, statusCode);
//
//		employeePayrollData = new Gson().fromJson(response.asString(), EmployeePayrollData.class);
//		employeePayrollRESTService.addEmployeeToPayroll(employeePayrollData, REST_IO);
//		long entries = employeePayrollRESTService.countEntries(REST_IO);
//		assertEquals(3, entries);
//	}
//
//	@Test
//	public void givenMultipleEmployee_WhenAdded_ShouldMatch201ResponseAndCount() {
//		EmployeePayrollData[] arrayOfEmployee = getEmployeeList();
//		employeePayrollRESTService = new EmployeePayrollRESTService(Arrays.asList(arrayOfEmployee));
//		EmployeePayrollData[] arrayOfEmployeePayroll = {
//				new EmployeePayrollData(0, "Charlie", "M", 4000000.00, LocalDate.now()),
//				new EmployeePayrollData(0, "Gunjan", "F", 5000000.00, LocalDate.now()) };
//
//		for (EmployeePayrollData employeePayrollData : arrayOfEmployeePayroll) {
//
//			Response response = addEmployeeToJsonServer(employeePayrollData);
//			int statusCode = response.getStatusCode();
//			assertEquals(201, statusCode);
//
//			employeePayrollData = new Gson().fromJson(response.asString(), EmployeePayrollData.class);
//			employeePayrollRESTService.addEmployeeToPayroll(employeePayrollData, REST_IO);
//		}
//		long entries = employeePayrollRESTService.countEntries(REST_IO);
//		assertEquals(5, entries);
//	}
//
//	@Test
//	public void givenSalary_WhenUpdated_ShouldMatch200response() {
//		EmployeePayrollData[] arrayOfEmployee = getEmployeeList();
//		employeePayrollRESTService = new EmployeePayrollRESTService(Arrays.asList(arrayOfEmployee));
//		employeePayrollRESTService.updateEmployeesalary("Charlie", 6000000.00, REST_IO);
//		EmployeePayrollData employeePayrollData = employeePayrollRESTService.getEmployeePayrollData("Charlie");
//
//		String empJson = new Gson().toJson(employeePayrollData);
//		RequestSpecification request = RestAssured.given();
//		request.header("Content-Type", "application/json");
//		request.body(empJson);
//		Response response = request.put("/employee_payroll/" + employeePayrollData.id);
//		int statusCode = response.getStatusCode();
//		assertEquals(200, statusCode);
//	}
	
	@Test
	public void givenEmployeeDataInJsonServer_WhenRetrieved_ShouldMatchCount() {
		EmployeePayrollData[] arrayOfEmployee = getEmployeeList();
		EmployeePayrollRESTService employeePayrollRESTService;
		employeePayrollRESTService = new EmployeePayrollRESTService(Arrays.asList(arrayOfEmployee));
		long entries = employeePayrollRESTService.countEntries(REST_IO);
		assertEquals(5, entries);
	}
}
