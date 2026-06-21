package com.lxbordo.api.dto;
import java.time.LocalDate;
import java.util.List;

public class VacationLeave {
    public int employeeIndex; // 0-based index
    public List<LocalDate> dates;
}