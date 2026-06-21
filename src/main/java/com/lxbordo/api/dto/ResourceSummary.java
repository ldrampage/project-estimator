package com.lxbordo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceSummary {
    private String name;
    private String workDescription;
    private double toalHours;
}