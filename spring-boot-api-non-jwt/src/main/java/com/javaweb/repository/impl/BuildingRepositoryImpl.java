package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaweb.DTO.BuildingDetailsDTO;
import com.javaweb.criteria.BuildingCriteria;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.utils.ConnectionUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private RentAreaRepository rentAreaRepository;
	
	@Override
	public List<BuildingDetailsDTO> findAll(BuildingCriteria buildingCriteria) {
		String sql = "SELECT building.* FROM Building ";
		String where = "WHERE 1=1";
		String join = "";
		String groupBy = " GROUP BY building.id ";
		boolean statusGroupBy = false;

		if (buildingCriteria.getName() != null && !buildingCriteria.getName().equals("")) {
			where += " AND name LIKE '%" + buildingCriteria.getName() + "%'";
		}
		if (buildingCriteria.getFloorArea() != null) {
			where += " AND floorarea = " + buildingCriteria.getFloorArea();
		}
		if (buildingCriteria.getDistrictId() != null) {
			where += " AND districtid = " + buildingCriteria.getDistrictId();
		}
		if (buildingCriteria.getWard() != null && !buildingCriteria.getWard().equals("")) {
			where += " AND ward LIKE '%" + buildingCriteria.getWard() + "%'";
		}
		if (buildingCriteria.getStreet() != null && !buildingCriteria.getStreet().equals("")) {
			where += " AND street LIKE '%" + buildingCriteria.getStreet() + "%'";
		}
		if (buildingCriteria.getNumberOfBasement() != null) {
			where += " AND numberofbasement = " + buildingCriteria.getNumberOfBasement();
		}
		if (buildingCriteria.getDirection() != null && !buildingCriteria.getDirection().equals("")) {
			where += " AND direction LIKE '%" + buildingCriteria.getDirection() + "%'";
		}
		if (buildingCriteria.getLevel() != null && !buildingCriteria.getLevel().equals("")) {
			where += " AND level LIKE '%" + buildingCriteria.getLevel() + "%'";
		}
		if (buildingCriteria.getManagerName() != null && !buildingCriteria.getManagerName().equals("")) {
			where += " AND managername LIKE '%" + buildingCriteria.getManagerName() + "%'";
		}
		if (buildingCriteria.getManagerPhoneNumber() != null && !buildingCriteria.getManagerPhoneNumber().equals("")) {
			where += " AND managerphonenumber LIKE '%" + buildingCriteria.getManagerPhoneNumber() + "%'";
		}
		if (buildingCriteria.getStaffId() != null) {
			join += " INNER JOIN assignmentbuilding ON building.id = assignmentbuilding.buildingid ";
			where += " AND staffid = " + buildingCriteria.getStaffId();
		}
		if (buildingCriteria.getTypeCode() != null && buildingCriteria.getTypeCode().length != 0) {
			join += " INNER JOIN buildingrenttype ON building.id = buildingrenttype.buildingid "
					+ " INNER JOIN renttype ON buildingrenttype.renttypeid = renttype.id ";
			where += " AND code IN (";
			for (String item : buildingCriteria.getTypeCode()) {
				where += "'" + item + "',";
			}
			where = where.substring(0, where.length() - 1);
			where += ") ";
			statusGroupBy = true;
		}
		if (buildingCriteria.getRentPriceFrom() != null) {
			where += " AND rentprice >= " + buildingCriteria.getRentPriceFrom();
		}
		if (buildingCriteria.getRentPriceTo() != null) {
			where += " AND rentprice <= " + buildingCriteria.getRentPriceTo();
		}
		if (!(buildingCriteria.getAreaFrom() == null && buildingCriteria.getAreaTo() == null)) {
			join += " INNER JOIN rentarea ON building.id = rentarea.buildingid ";
			statusGroupBy = true;
		}
		if (buildingCriteria.getAreaFrom() != null) {
			where += " AND value >= " + buildingCriteria.getAreaFrom();
		}
		if (buildingCriteria.getAreaTo() != null) {
			where += " AND value <= " + buildingCriteria.getAreaTo();
		}
		sql += statusGroupBy ? join + where + groupBy : join + where;

		List<BuildingDetailsDTO> result = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(sql)) {
			while (rs.next()) {
				BuildingDetailsDTO building = new BuildingDetailsDTO();
				building.setName(rs.getString("name"));
				building.setDistrictId(rs.getLong("districtid"));
				building.setStreet(rs.getString("street"));
				building.setWard(rs.getString("ward"));
				building.setNumberOfBasement(rs.getLong("numberofbasement"));
				building.setManagerName(rs.getString("managername"));
				building.setManagerPhoneNumber(rs.getString("managerphonenumber"));
				building.setFloorArea(rs.getLong("floorarea"));
				building.setRentPrice(rs.getLong("rentprice"));
				building.setBrokerageFee(rs.getDouble("brokeragefee"));
				building.setServiceFee(rs.getString("servicefee"));
				building.setDistrictName(districtRepository.getDistrictNameById(rs.getLong("id")));
				building.setRentArea(rentAreaRepository.getRentAreaById(rs.getLong("id")));
				result.add(building);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection failed");
		}

		return result;
	}

	@Override
	public void delete(Long[] ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}

}
