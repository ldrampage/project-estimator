package com.lxbordo.api.dto;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProjectRequest {

	@NotNull(message="employees is required.")
    private Map<String, Employee> employees;

	@NotNull(message="projectStartDt is required.")
    private LocalDate projectStartDt;

	@NotNull(message="estimatedWorkHours is required.")
    private double estimatedWorkHours;

    private List<LocalDate> holidaysAlongTheWay;

	public Map<String, Employee> getEmployees() {
		return employees;
	}
  
}