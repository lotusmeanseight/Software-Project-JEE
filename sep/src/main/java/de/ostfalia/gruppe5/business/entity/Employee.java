package de.ostfalia.gruppe5.business.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQueries({ @NamedQuery(name = "Employee.countAll", query = "SELECT COUNT(e) FROM Employee e"),
		@NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e") })
@Entity
@Table(name = "employees")
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Employee {

	@Id
	private Integer employeeNumber;

	@OneToMany(mappedBy = "salesRepEmployeeNumber", fetch = FetchType.LAZY)
	private List<Customer> customers = new ArrayList<>();

	@NotNull
	@Size(max = 50)
	private String lastName;

	@NotNull
	@Size(max = 50)
	private String firstName;

	@NotNull
	@Size(max = 10)
	private String extension;

	@NotNull
	@Size(max = 100)
	private String email;

	@ManyToOne
	@JoinColumn(name = "officeCode")
	private Office officeCode;

	private Integer reportsTo;

	@NotNull
	@Size(max = 50)
	private String jobTitle;

	public Integer getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(Integer employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Office getOfficeCode() {
		return officeCode;
	}

	public Integer getReportsTo() {
		return reportsTo;
	}

	public void setReportsTo(Integer reportsTo) {
		this.reportsTo = reportsTo;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Employee employee = (Employee) o;
		return Objects.equals(getEmployeeNumber(), employee.getEmployeeNumber())
				&& Objects.equals(customers, employee.customers)
				&& Objects.equals(getLastName(), employee.getLastName())
				&& Objects.equals(getFirstName(), employee.getFirstName())
				&& Objects.equals(getExtension(), employee.getExtension())
				&& Objects.equals(getEmail(), employee.getEmail())
				&& Objects.equals(getOfficeCode(), employee.getOfficeCode())
				&& Objects.equals(getReportsTo(), employee.getReportsTo())
				&& Objects.equals(getJobTitle(), employee.getJobTitle());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getEmployeeNumber(), customers, getLastName(), getFirstName(), getExtension(), getEmail(),
				getOfficeCode(), getReportsTo(), getJobTitle());
	}

	public void setOfficeCode(Office officeCode) {
		this.officeCode = officeCode;
	}

}
