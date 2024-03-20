package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.DTO.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	private BuildingRepository buildingRepository;
	
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private RentAreaRepository rentAreaRepository;
	
	@Override
	public List<BuildingDTO> findAll(Map<String, String> params) {
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(params);
		List<BuildingDTO> result = new ArrayList<BuildingDTO>();
		
		for (BuildingEntity item : buildingEntities) {
			String districtName = districtRepository.getDistrictNameByBuildingId(item.getId());
			List<Long> rentAreas = rentAreaRepository.getRentAreaByBuildingId(item.getId());
			BuildingDTO building = new BuildingDTO();
			building.setName(item.getName());
			building.setAdress(item.getStreet() + ", " + item.getWard() + ", " + districtName);
			building.setNumberOfBasement(item.getNumberOfBasement());
			building.setManagerName(item.getManagerName());
			building.setManagerPhoneNumber(item.getManagerPhoneNumber());
			building.setFloorArea(item.getFloorArea());
			building.setRentPrice(item.getRentPrice());
			building.setBrokerageFee(item.getBrokerageFee());
			building.setServiceFee(item.getServiceFee());
			String rentArea = String.join(", ", rentAreas.stream().map(Object::toString).collect(Collectors.toList()));
			building.setRentArea(rentArea);
			result.add(building);
		}
		
		return result;
	}
	
}
