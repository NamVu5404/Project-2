package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaweb.DTO.request.BuildingRequestDTO;
import com.javaweb.DTO.response.BuildingResponseDTO;
import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.converters.BuildingConverter;
import com.javaweb.converters.BuildingSearchBuilderConverter;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.service.BuildingService;

@Service
public class BuildingServiceImpl implements BuildingService {

	@Autowired
	private BuildingRepository buildingRepository;

	@Autowired
	private BuildingConverter buildingConverter;

	@Autowired
	private BuildingSearchBuilderConverter buildingSearchBuilderConverter;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<BuildingResponseDTO> findAll(Map<String, Object> params, List<String> typeCode) {
		BuildingSearchBuilder buildingSearchBuilder = buildingSearchBuilderConverter.toBuildingSearchBuilder(params,
				typeCode);
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(buildingSearchBuilder);
		List<BuildingResponseDTO> result = new ArrayList<BuildingResponseDTO>();

		for (BuildingEntity item : buildingEntities) {
			BuildingResponseDTO buildingResponseDTO = buildingConverter.toBuildingResponseDTO(item);
			result.add(buildingResponseDTO);
		}

		return result;
	}

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void createBuilding(BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildingEntity = modelMapper.map(buildingRequestDTO, BuildingEntity.class);
		buildingRepository.createBuilding(buildingEntity);
	}

	@Override
	public void updateBuilding(BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildingEntity = modelMapper.map(buildingRequestDTO, BuildingEntity.class);
		buildingRepository.updateBuilding(buildingEntity);
	}

	@Override
	public void deleteBuilding(Long id) {
		BuildingEntity buildingEntity = entityManager.find(BuildingEntity.class, id);
		buildingRepository.deleteBuilding(buildingEntity);
	}

}
