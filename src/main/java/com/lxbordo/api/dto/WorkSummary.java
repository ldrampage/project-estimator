package com.lxbordo.api.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WorkSummary {
    private String workDescription;
    private double totalHours;
    private List<ResourceSummary> resources;
}