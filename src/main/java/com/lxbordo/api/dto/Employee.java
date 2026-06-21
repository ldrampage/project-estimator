package com.lxbordo.api.dto;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Employee {

    private String name;
    private String workDescription;
    @NotBlank(message="workHours is required")
    @NotNull(message="workHours is required")
    private double workHours;
    private double totalWorkHours;
    private List<LocalDate> vls;
    
    
}