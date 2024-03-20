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

	public String whereQuery(Map<String, String> params) {
		StringBuilder where = new StringBuilder("WHERE 1=1");

		if (params.get("name") != null && !params.get("name").equals("")) {
			where.append(" AND name LIKE '%").append(params.get("name")).append("%'");
		}
		if (params.get("floorArea") != null && !params.get("floorArea").equals("")) {
			where.append(" AND floorarea = ").append(params.get("floorArea"));
		}
		if (params.get("districtId") != null && !params.get("districtId").equals("")) {
			where.append(" AND districtid = ").append(params.get("districtId"));
		}
		if (params.get("ward") != null && !params.get("ward").equals("")) {
			where.append(" AND ward LIKE '%").append(params.get("ward")).append("%'");
		}
		if (params.get("street") != null && !params.get("street").equals("")) {
			where.append(" AND street LIKE '%").append(params.get("street")).append("%'");
		}
		if (params.get("numberOfBasement") != null && !params.get("numberOfBasement").equals("")) {
			where.append(" AND numberofbasement = ").append(params.get("numberOfBasement"));
		}
		if (params.get("direction") != null && !params.get("direction").equals("")) {
			where.append(" AND direction LIKE '%").append(params.get("direction")).append("%'");
		}
		if (params.get("level") != null && !params.get("level").equals("")) {
			where.append(" AND level LIKE '%").append(params.get("level")).append("%'");
		}
		if (params.get("managerName") != null && !params.get("managerName").equals("")) {
			where.append(" AND managername LIKE '%").append(params.get("managerName")).append("%'");
		}
		if (params.get("managerPhoneNumber") != null && !params.get("managerPhoneNumber").equals("")) {
			where.append(" AND managerphonenumber LIKE '%").append(params.get("managerPhoneNumber")).append("%'");
		}
		if (params.get("rentPriceFrom") != null && !params.get("rentPriceFrom").equals("")) {
			where.append(" AND rentprice >= ").append(params.get("rentPriceFrom"));
		}
		if (params.get("rentPriceTo") != null && !params.get("rentPriceTo").equals("")) {
			where.append(" AND rentprice <= ").append(params.get("rentPriceTo"));
		}
		if (params.get("areaFrom") != null && !params.get("areaFrom").equals("")) {
			where.append(" AND value >= ").append(params.get("areaFrom"));
		}
		if (params.get("areaTo") != null && !params.get("areaTo").equals("")) {
			where.append(" AND value <= ").append(params.get("areaTo"));
		}
		if (params.get("staffId") != null && !params.get("staffId").equals("")) {
			where.append(" AND staffid = ").append(params.get("staffId"));
		}
		if (params.get("typeCode") != null && !params.get("typeCode").equals("")) {
			String[] typeCodes = params.get("typeCode").split(",");
			where.append(" AND code IN (");
			for (String item : typeCodes) {
				where.append("'").append(item).append("',");
			}
			where.setLength(where.length() - 1);
			where.append(") ");
		}

		return where.toString();
	}

	public String joinQuery(Map<String, String> params) {
		StringBuilder join = new StringBuilder();

		if (params.get("staffId") != null && !params.get("staffId").equals("")) {
			join.append(" INNER JOIN assignmentbuilding ON building.id = assignmentbuilding.buildingid ");
		}
		if (params.get("typeCode") != null && !params.get("typeCode").equals("")) {
			join.append(" INNER JOIN buildingrenttype ON building.id = buildingrenttype.buildingid ")
					.append(" INNER JOIN renttype ON buildingrenttype.renttypeid = renttype.id ");
		}
		if (!(params.get("areaFrom") == null && params.get("areaTo") == null)) {
			join.append(" INNER JOIN rentarea ON building.id = rentarea.buildingid ");
		}

		return join.toString();
	}

	@Override
	public List<BuildingEntity> findAll(Map<String, String> params) {
		String sql = "SELECT building.* FROM Building ";
		String where = whereQuery(params);
		String join = joinQuery(params);
		String groupBy = " GROUP BY building.id ";
		if (params.get("typeCode") != null || !(params.get("areaFrom") == null && params.get("areaTo") == null)) {
			sql += join + where + groupBy;
		} else {
			sql += join + where;
		}

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
