package com.lxbordo.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lxbordo.api.dto.ProjectRequest;
import com.lxbordo.api.service.ProjectEstimatorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/pe")
@RequiredArgsConstructor
@Slf4j
public class ProjectEstimatorController {
	
	private final ProjectEstimatorService projectEstServ;
	
	@PostMapping("/calculate")
	public ResponseEntity<?> calculate(@RequestBody @Valid ProjectRequest projectRequest) {
		log.debug("begin:: calculate");
		return ResponseEntity.ok(projectEstServ.calculateEndDate(projectRequest));
		
	}

}
