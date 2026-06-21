package com.lxbordo.api.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({"startDt", "estimatedEndDate","estimatedWorkHours","estimatedWorkingDays", "workSumList"})
public class ProjectResponse {
	
	private LocalDate startDt;
	private double estimatedWorkHours;
	private double resourceAllocatedHours;
	private LocalDate estimatedEndDate;
	private String estimatedWorkingDays;
	private List<WorkSummary> workSumList;

}
