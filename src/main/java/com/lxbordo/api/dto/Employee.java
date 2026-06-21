package com.lxbordo.api.dto;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Employee {

    private String name;
    private List<LocalDate> vls = null;
    
    @NotBlank(message="workHours is required")
    @NotNull(message="workHours is required")
    private double workHours;
    
}