package com.javaweb.service;

import java.util.List;

import com.javaweb.DTO.BuildingDTO;
import com.javaweb.criteria.BuildingCriteria;

public interface BuildingService {
	List<BuildingDTO> findAll(BuildingCriteria buildingCriteria);
}
