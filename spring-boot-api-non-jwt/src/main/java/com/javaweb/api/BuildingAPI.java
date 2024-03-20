package com.javaweb.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.DTO.BuildingDTO;
import com.javaweb.service.BuildingService;

@RestController
public class BuildingAPI {

	@Autowired
	private BuildingService buildingService;

	@GetMapping
	public List<BuildingDTO> findBuilding(@RequestParam Map<String, String> params) {
		List<BuildingDTO> result = buildingService.findAll(params);
		return result;
	}

//	public void validateData(BuildingDTO buildingDTO) {
//		if (buildingDTO.getName() == null || buildingDTO.equals("") || buildingDTO.getDistrictId() == null) {
//			throw new FieldRequiredException("Name or districtId is null");
//		}
//	}

	@PostMapping(value = "/api/building")
	public Object createBuilding(@RequestBody BuildingDTO buildingDTO) {
//		validateData(buildingDTO);
		return buildingDTO;
	}

	@DeleteMapping(value = "/api/building/{ids}/{name}")
	public void deleteBuilding(@PathVariable Long[] ids, @PathVariable String name) {
		System.out.println(name);
	}
}
