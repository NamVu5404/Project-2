package com.javaweb.converters;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.DTO.response.BuildingResponseDTO;
import com.javaweb.repository.entity.BuildingEntity;

@Component
public class BuildingConverter {

	@Autowired
	private ModelMapper modelMapper;
	
	public BuildingResponseDTO toBuildingResponseDTO(BuildingEntity item) {
		BuildingResponseDTO building = modelMapper.map(item, BuildingResponseDTO.class);
		building.setAddress(item.getStreet() + ", " + item.getWard() + ", " + item.getDistrict().getName());
		String rentAreas = item.getRentAreas().stream().map(it -> it.getValue().toString()).collect(Collectors.joining(","));
		building.setRentArea(rentAreas);
		
		return building;
	}
}
