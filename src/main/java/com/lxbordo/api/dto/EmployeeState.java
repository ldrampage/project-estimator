package com.lxbordo.api.dto;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class EmployeeState {

    double workHours;
    Set<LocalDate> vlSet;

    public EmployeeState(double workHours, List<LocalDate> vls) {
        this.workHours = workHours;
        this.vlSet = new HashSet<>(vls);
    }
    
    public boolean isOnLeave(LocalDate date) {
        return vlSet.contains(date);
    }
    
}