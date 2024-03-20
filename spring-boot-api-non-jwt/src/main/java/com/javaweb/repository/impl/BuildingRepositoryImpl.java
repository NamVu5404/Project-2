package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.ConnectionUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

	@Override
	public List<BuildingEntity> findAll(Map<String, String> params) {
		String sql = "SELECT building.* FROM Building ";
		String where = "WHERE 1=1";
		String join = "";
		String groupBy = " GROUP BY building.id ";
		boolean statusGroupBy = false;

		if (params.get("name") != null && !params.get("name").equals("")) {
			where += " AND name LIKE '%" + params.get("name") + "%'";
		}
		if (params.get("floorArea") != null && !params.get("floorArea").equals("")) {
			where += " AND floorarea = " + params.get("floorArea");
		}
		if (params.get("districtId") != null && !params.get("districtId").equals("")) {
			where += " AND districtid = " + params.get("districtId");
		}
		if (params.get("ward") != null && !params.get("ward").equals("")) {
			where += " AND ward LIKE '%" + params.get("ward") + "%'";
		}
		if (params.get("street") != null && !params.get("street").equals("")) {
			where += " AND street LIKE '%" + params.get("street") + "%'";
		}
		if (params.get("numberOfBasement") != null && !params.get("numberOfBasement").equals("")) {
			where += " AND numberofbasement = " + params.get("numberOfBasement");
		}
		if (params.get("direction") != null && !params.get("direction").equals("")) {
			where += " AND direction LIKE '%" + params.get("direction") + "%'";
		}
		if (params.get("level") != null && !params.get("level").equals("")) {
			where += " AND level LIKE '%" + params.get("level") + "%'";
		}
		if (params.get("managerName") != null && !params.get("managerName").equals("")) {
			where += " AND managername LIKE '%" + params.get("managerName") + "%'";
		}
		if (params.get("managerPhoneNumber") != null && !params.get("managerPhoneNumber").equals("")) {
			where += " AND managerphonenumber LIKE '%" + params.get("managerPhoneNumber") + "%'";
		}
		if (params.get("staffId") != null && !params.get("staffId").equals("")) {
			join += " INNER JOIN assignmentbuilding ON building.id = assignmentbuilding.buildingid ";
			where += " AND staffid = " + params.get("staffId");
		}
		if (params.get("typeCode") != null && !params.get("typeCode").equals("")) {
			String[] typeCodes = params.get("typeCode").split(",");
			join += " INNER JOIN buildingrenttype ON building.id = buildingrenttype.buildingid "
					+ " INNER JOIN renttype ON buildingrenttype.renttypeid = renttype.id ";
			where += " AND code IN (";
			for (String item : typeCodes) {
				where += "'" + item + "',";
			}
			where = where.substring(0, where.length() - 1);
			where += ") ";
			statusGroupBy = true;
		}
		if (params.get("rentPriceFrom") != null && !params.get("rentPriceFrom").equals("")) {
			where += " AND rentprice >= " + params.get("rentPriceFrom");
		}
		if (params.get("rentPriceTo") != null && !params.get("rentPriceTo").equals("")) {
			where += " AND rentprice <= " + params.get("rentPriceTo");
		}
		if (!(params.get("areaFrom") == null && params.get("areaTo") == null)) {
			join += " INNER JOIN rentarea ON building.id = rentarea.buildingid ";
			statusGroupBy = true;
		}
		if (params.get("areaFrom") != null && !params.get("areaFrom").equals("")) {
			where += " AND value >= " + params.get("areaFrom");
		}
		if (params.get("areaTo") != null && !params.get("areaTo").equals("")) {
			where += " AND value <= " + params.get("areaTo");
		}
		sql += statusGroupBy ? join + where + groupBy : join + where;

		List<BuildingEntity> result = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(sql)) {
			while (rs.next()) {
				BuildingEntity building = new BuildingEntity();
				building.setId(rs.getLong("id"));
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
