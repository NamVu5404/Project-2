package com.javaweb.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

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

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<BuildingResponseDTO> findAll(Map<String, Object> params, List<String> typeCode) {
		BuildingSearchBuilder buildingSearchBuilder = buildingSearchBuilderConverter.toBuildingSearchBuilder(params,
				typeCode);
		List<BuildingEntity> buildingEntities = buildingRepository.findAll(buildingSearchBuilder);
//		List<BuildingEntity> buildingEntities = buildingRepository
//				.findByNameContainingAndWardContaining(buildingSearchBuilder.getName(), "Phuong 6");
//		BuildingEntity buildingEntity= buildingRepository.findById(1L).get();
		List<BuildingResponseDTO> result = new ArrayList<BuildingResponseDTO>();

		for (BuildingEntity item : buildingEntities) {
			BuildingResponseDTO buildingResponseDTO = buildingConverter.toBuildingResponseDTO(item);
			result.add(buildingResponseDTO);
		}

		return result;
	}

	@Override
	public void create(BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildingEntity = modelMapper.map(buildingRequestDTO, BuildingEntity.class);
		buildingRepository.save(buildingEntity);
	}

	@Transactional
	@Override
	public void delete(Long[] ids) {
		buildingRepository.deleteByIdIn(ids);
	}

}
