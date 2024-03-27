package com.javaweb.converters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.DTO.BuildingDTO;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;

@Component
public class BuildingConverter {
	
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private RentAreaRepository rentAreaRepository;
	
	public BuildingDTO toBuildingDTO(BuildingEntity item) {
		DistrictEntity districtEntity = districtRepository.findById(item.getDistrictId());
		List<String> rentAreas = rentAreaRepository.findByBuildingId(item.getId());
		BuildingDTO building = new BuildingDTO();
		building.setName(item.getName());
		building.setAdress(item.getStreet() + ", " + item.getWard() + ", " + districtEntity.getName());
		building.setNumberOfBasement(item.getNumberOfBasement());
		building.setManagerName(item.getManagerName());
		building.setManagerPhoneNumber(item.getManagerPhoneNumber());
		building.setFloorArea(item.getFloorArea());
		building.setRentPrice(item.getRentPrice());
		building.setBrokerageFee(item.getBrokerageFee());
		building.setServiceFee(item.getServiceFee());
		building.setRentArea(String.join(", ", rentAreas));
		
		return building;
	}
}
