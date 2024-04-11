package com.javaweb.api;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.DTO.request.BuildingRequestDTO;
import com.javaweb.DTO.response.BuildingResponseDTO;
import com.javaweb.service.BuildingService;

@RestController
public class BuildingAPI {

	@Autowired
	private BuildingService buildingService;

	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping
	public List<BuildingResponseDTO> findBuilding(@RequestParam Map<String, Object> params,
			@RequestParam(value = "typeCode", required = false) List<String> typeCode) {
		List<BuildingResponseDTO> result = buildingService.findAll(params, typeCode);
		return result;
	}

//	public void validateData(BuildingDTO buildingDTO) {
//		if (buildingDTO.getName() == null || buildingDTO.equals("") || buildingDTO.getDistrictId() == null) {
//			throw new FieldRequiredException("Name or districtId is null");
//		}
//	}

	@PostMapping(value = "/api/building")
	public void create(@RequestBody BuildingRequestDTO buildingRequestDTO) {
//		validateData(buildingDTO);
		buildingService.create(buildingRequestDTO);
	}

	@DeleteMapping(value = "/api/building/{ids}")
	public void delete(@PathVariable Long[] ids) {
		buildingService.delete(ids);
	}
}
