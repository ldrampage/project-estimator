package com.lxbordo.api.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lxbordo.api.dto.Employee;
import com.lxbordo.api.dto.EmployeeState;
import com.lxbordo.api.dto.ProjectRequest;
import com.lxbordo.api.dto.ProjectResponse;
import com.lxbordo.api.dto.ResourceSummary;
import com.lxbordo.api.dto.Response;
import com.lxbordo.api.dto.WorkSummary;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProjectEstimatorService {

	public Response<ProjectResponse> calculateEndDate(ProjectRequest request) {
		log.debug("begin: calculateEndDate");

		LocalDate startDt = request.getProjectStartDt();
		double remainingWork = request.getEstimatedWorkHours();

		int workingDays = 0;
		double remainingEffort = 0;

		Set<LocalDate> holidays = new HashSet<>(
				Optional.ofNullable(request.getHolidaysAlongTheWay()).orElse(Collections.emptyList()));

		Map<String, EmployeeState> states = new HashMap<>();

		// build employee state
		states = this.buildEmployeeSet(request.getEmployees());

		while (remainingWork > 0) {
			// skip weekends
			if (isWeekend(startDt) || holidays.contains(startDt)) {
				startDt = startDt.plusDays(1);
				continue;
			}

			double dailyCapacity = this.getDailyCapacity(states, startDt);

			// safety guard (no work happens today)
			if (dailyCapacity == 0) {
				startDt = startDt.plusDays(1);
				continue;
			}
			log.debug("Team effort per day: " + dailyCapacity);
			remainingWork -= dailyCapacity;

			workingDays++;
			startDt = startDt.plusDays(1);

			if (remainingWork < 0) {
				remainingEffort = remainingWork + dailyCapacity;

			}

		}
		
		ProjectResponse projectResponse = new ProjectResponse();
		projectResponse.setStartDt(request.getProjectStartDt());
		projectResponse.setEstimatedWorkHours(request.getEstimatedWorkHours());
		projectResponse.setResourceAllocatedHours(request.getEstimatedWorkHours() + Math.abs(remainingWork));
		projectResponse.setEstimatedEndDate(startDt.minusDays(1));
		projectResponse
				.setEstimatedWorkingDays(String.valueOf(workingDays) + " days and " + remainingEffort + " hours.");

		List<Employee> employeeList = new ArrayList<Employee>(request.getEmployees().values());
		projectResponse.setWorkSumList(this.aggregate(employeeList, workingDays));

		return Response.<ProjectResponse>builder().statusCode(HttpStatus.OK.value())
				.message("Report Generated Successfully.").data(projectResponse).build();

	}

	// build employee state
	private Map<String, EmployeeState> buildEmployeeSet(Map<String, Employee> empMap) {
		Map<String, EmployeeState> states = new HashMap<>();

		for (Map.Entry<String, Employee> entry : empMap.entrySet()) {

			Employee e = entry.getValue();
			List<LocalDate> vls = Optional.ofNullable(e.getVls()).orElse(Collections.emptyList());

			states.put(entry.getKey(), new EmployeeState(e.getWorkHours(), vls));
		}
		return states;
	}

	private double getDailyCapacity(Map<String, EmployeeState> states, LocalDate startDt) {
		double dailyCapacity = 0;
		for (EmployeeState state : states.values()) {

			if (!state.isOnLeave(startDt)) {
				dailyCapacity += state.getWorkHours();
			}
		}
		return dailyCapacity;
	}

	private boolean isWeekend(LocalDate date) {
		return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
	}

	private List<WorkSummary> aggregate(List<Employee> employees, double estimatedWorkingDays) {
		log.debug("begin: aggregate");
		Map<String, List<Employee>> grouped = employees.stream()
				.collect(Collectors.groupingBy(Employee::getWorkDescription));

		List<WorkSummary> result = new ArrayList<>();

		// set the total hours per employee
		for (Employee emp : employees) {
			emp.setTotalWorkHours(estimatedWorkingDays * emp.getWorkHours());
		}

		for (Map.Entry<String, List<Employee>> entry : grouped.entrySet()) {

			String workDesc = entry.getKey();
			List<Employee> empList = entry.getValue();

			double totalHours = empList.stream().mapToDouble(Employee::getTotalWorkHours).sum();

			List<ResourceSummary> resources = empList.stream()
					.map(e -> new ResourceSummary(e.getName(), e.getWorkDescription(), e.getTotalWorkHours()))
					.collect(Collectors.toList());

			result.add(new WorkSummary(workDesc, totalHours, resources));
		}
		log.debug("end: aggregate");
		return result;
	}

}