package com.javaweb.repository;

import java.util.List;

import com.javaweb.DTO.BuildingDetailsDTO;
import com.javaweb.criteria.BuildingCriteria;

public interface BuildingRepository {
	List<BuildingDetailsDTO> findAll(BuildingCriteria buildingCriteria);
	void delete(Long[] ids);
	void create();
}
