package com.javaweb.repository;

import java.util.List;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.entity.BuildingEntity;

public interface BuildingRepository {
	List<BuildingEntity> findAll(BuildingSearchBuilder builder);
	void createBuilding(BuildingEntity buildingEntity);
	void updateBuilding(BuildingEntity buildingEntity);
	void deleteBuilding(BuildingEntity buildingEntity);
}
