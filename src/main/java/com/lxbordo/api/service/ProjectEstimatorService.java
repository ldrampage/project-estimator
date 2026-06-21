package com.lxbordo.api.service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lxbordo.api.dto.Employee;
import com.lxbordo.api.dto.EmployeeState;
import com.lxbordo.api.dto.ProjectRequest;
import com.lxbordo.api.dto.ProjectResponse;
import com.lxbordo.api.dto.Response;

@Service
public class ProjectEstimatorService {

    public Response<ProjectResponse> calculateEndDate(ProjectRequest request) {

        LocalDate currentDate = request.getProjectStartDt();
        double remainingWork = request.getEstimatedWorkHours();

        Set<LocalDate> holidays = new HashSet<>(
                Optional.ofNullable(request.getHolidaysAlongTheWay())
                        .orElse(Collections.emptyList())
        );

        Map<String, EmployeeState> states = new HashMap<>();

        // build employee state
        for (Map.Entry<String, Employee> entry : request.getEmployees().entrySet()) {

            Employee e = entry.getValue();

            List<LocalDate> vls = Optional.ofNullable(e.getVls())
                    .orElse(Collections.emptyList());

            states.put(entry.getKey(),
                    new EmployeeState(e.getWorkHours(), vls));
        }

        while (remainingWork > 0) {

            // skip weekends
            if (isWeekend(currentDate) || holidays.contains(currentDate)) {
                currentDate = currentDate.plusDays(1);
                continue;
            }

            double dailyCapacity = 0;

            for (EmployeeState state : states.values()) {

                if (!state.isOnLeave(currentDate)) {
                    dailyCapacity += state.getWorkHours();
                }
            }

            // safety guard (no work happens today)
            if (dailyCapacity == 0) {
                currentDate = currentDate.plusDays(1);
                continue;
            }

            remainingWork -= dailyCapacity;

            currentDate = currentDate.plusDays(1);
        }
        
        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setProjectEndDate(currentDate);
        
        return Response.<ProjectResponse>builder()
        		.statusCode(HttpStatus.OK.value())
        		.message("End Date Generated Successfully.")
        		.data(projectResponse)
        		.build();
   
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY ||
               date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}