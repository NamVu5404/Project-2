package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.DTO.BuildingDTO;
import com.javaweb.DTO.BuildingDetailsDTO;
import com.javaweb.criteria.BuildingCriteria;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	private BuildingRepository buildingRepository;
	
	@Override
	public List<BuildingDTO> findAll(BuildingCriteria buildingCriteria) {
		List<BuildingDetailsDTO> buildingDetailsDTOs = buildingRepository.findAll(buildingCriteria);
		List<BuildingDTO> result = new ArrayList<BuildingDTO>();
		for (BuildingDetailsDTO item : buildingDetailsDTOs) {
			BuildingDTO building = new BuildingDTO();
			building.setName(item.getName());
			building.setAdress(item.getStreet() + ", " + item.getWard() + ", " + item.getDistrictName());
			building.setNumberOfBasement(item.getNumberOfBasement());
			building.setManagerName(item.getManagerName());
			building.setManagerPhoneNumber(item.getManagerPhoneNumber());
			building.setFloorArea(item.getFloorArea());
			building.setRentPrice(item.getRentPrice());
			building.setBrokerageFee(item.getBrokerageFee());
			building.setServiceFee(item.getServiceFee());
			String rentArea = "";
			for (Long value : item.getRentArea()) {
				rentArea += String.valueOf(value) + ", ";
			}
			rentArea = rentArea.substring(0, rentArea.length() - 2);
			building.setRentArea(rentArea);
			result.add(building);
		}
		return result;
	}
	
}
