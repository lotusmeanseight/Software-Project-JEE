package de.ostfalia.gruppe5.views.comparators;

import java.util.Comparator;

import de.ostfalia.gruppe5.business.entity.Employee;

public class EmployeeComparator implements Comparator<Employee> {

	@Override
	public int compare(Employee emp1, Employee emp2) {
		return emp1.getEmployeeNumber().compareTo(emp2.getEmployeeNumber());
	}
}
