package com.javaweb.service;

import java.util.List;
import java.util.Map;

import com.javaweb.DTO.request.BuildingRequestDTO;
import com.javaweb.DTO.response.BuildingResponseDTO;

public interface BuildingService {
	List<BuildingResponseDTO> findAll(Map<String, Object> params, List<String> typeCode);
	void createBuilding(BuildingRequestDTO buildingRequestDTO);
	void updateBuilding(BuildingRequestDTO buildingRequestDTO);
	void deleteBuilding(Long id);
}
