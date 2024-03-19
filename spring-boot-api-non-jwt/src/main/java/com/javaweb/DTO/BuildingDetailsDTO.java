package com.javaweb.DTO;

import java.util.List;

import com.javaweb.repository.entity.BuildingEntity;

public class BuildingDetailsDTO extends BuildingEntity {
	private String districtName;
	private List<Long> rentArea;

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public List<Long> getRentArea() {
		return rentArea;
	}

	public void setRentArea(List<Long> rentArea) {
		this.rentArea = rentArea;
	}

}
