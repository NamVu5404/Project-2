package com.javaweb.converters;

import java.util.List;

import org.modelmapper.ModelMapper;
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
	
	@Autowired
	private ModelMapper modelMapper;
	
	public BuildingDTO toBuildingDTO(BuildingEntity item) {
		BuildingDTO building = modelMapper.map(item, BuildingDTO.class);
		DistrictEntity districtEntity = districtRepository.findById(item.getDistrictId());
		List<String> rentAreas = rentAreaRepository.findByBuildingId(item.getId());
		building.setAddress(item.getStreet() + ", " + item.getWard() + ", " + districtEntity.getName());
		building.setRentArea(String.join(", ", rentAreas));
		
		return building;
	}
}
