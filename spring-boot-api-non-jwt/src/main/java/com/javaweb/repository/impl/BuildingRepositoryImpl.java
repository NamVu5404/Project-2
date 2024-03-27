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
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

	public void queryJoin(Map<String, Object> params, List<String> typeCode, StringBuilder sql) {
		String areaFrom = (String) params.get("areaFrom");
		String areaTo = (String) params.get("areaTo");
		if (StringUtil.checkString(areaFrom) || StringUtil.checkString(areaTo)) {
			sql.append(" JOIN rentarea r ON r.buildingid = b.id ");
		}
		String staffId = (String) params.get("staffId");
		if (StringUtil.checkString(staffId)) {
			sql.append(" JOIN assignmentbuilding asm ON asm.buildingid = b.id ");
		}
		if (typeCode != null && !typeCode.isEmpty()) {
			sql.append(" JOIN buildingrenttype brt ON brt.buildingid = b.id "
					+ " JOIN renttype rt ON rt.id = brt.renttypeid ");
		}
	}
	
	public void queryWhereNormal(Map<String, Object> params, StringBuilder where) {
		for (Map.Entry<String, Object> item : params.entrySet()) {
			if (!item.getKey().equals("staffId") && !item.getKey().equals("typeCode") 
					&& !item.getKey().startsWith("area") && !item.getKey().startsWith("rentPrice")) {
				String data = item.getValue().toString();
				if (StringUtil.checkString(data)) {
					if (NumberUtil.checkNumber(data)) {
						where.append(" AND b." + item.getKey().toLowerCase() + " = " + data);
					} else {
						where.append(" AND b." + item.getKey().toLowerCase() + " LIKE '%" + data + "%'");
					}
				}
			}
		}
	}
	
	public void queryWhereSpecial(Map<String, Object> params, List<String> typeCode, StringBuilder where) {
		String staffId = (String) params.get("staffId");
		if (StringUtil.checkString(staffId)) {
			where.append(" AND asm.staffid = " + staffId);
		}
		String areaFrom = (String) params.get("areaFrom");
		String areaTo = (String) params.get("areaTo");
		if (StringUtil.checkString(areaFrom)) {
			where.append(" AND r.value >= " + areaFrom);
		}
		if (StringUtil.checkString(areaTo)) {
			where.append(" AND r.value <= " + areaTo);
		}
		String rentPriceFrom = (String) params.get("rentPriceFrom");
		String rentPriceTo = (String) params.get("rentPriceTo");
		if (StringUtil.checkString(rentPriceFrom)) {
			where.append(" AND b.rentprice >= " + rentPriceFrom);
		}
		if (StringUtil.checkString(rentPriceTo)) {
			where.append(" AND b.rentprice <= " + rentPriceTo);
		}
		if (typeCode != null && !typeCode.isEmpty()) {
			where.append(" AND rt.code IN (");
			for (int i = 0; i < typeCode.size(); i++) {
				where.append("'" + typeCode.get(i) + "'");
				if (i < typeCode.size() - 1) {
					where.append(", ");
				}
			}
			where.append(") ");
		}
	}
 
	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode) {
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.districtid, b.street, "
				+ "b.ward, b.numberofbasement, b.managername, "
				+ "b.managerphonenumber, b.floorarea, b.rentprice, b.brokeragefee, b.servicefee "
				+ "FROM Building b ");
		queryJoin(params, typeCode, sql);
		
		StringBuilder where = new StringBuilder("WHERE 1 = 1 ");
		queryWhereNormal(params, where);
		queryWhereSpecial(params, typeCode, where);
		sql.append(where);
		sql.append(" GROUP BY b.id");

		List<BuildingEntity> result = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(sql.toString())) {
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
