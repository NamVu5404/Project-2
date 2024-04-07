package com.javaweb.repository.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.ConnectionUtil;
import com.javaweb.utils.StringUtil;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

	public void queryJoin(BuildingSearchBuilder builder, StringBuilder sql) {
		Long areaFrom = builder.getAreaFrom();
		Long areaTo = builder.getAreaTo();
		if (areaFrom != null || areaTo != null) {
			sql.append(" JOIN rentarea r ON r.buildingid = b.id ");
		}
		Long staffId = builder.getStaffId();
		if (staffId != null) {
			sql.append(" JOIN assignmentbuilding asm ON asm.buildingid = b.id ");
		}
		List<String> typeCode = builder.getTypeCode();
		if (typeCode != null && !typeCode.isEmpty()) {
			sql.append(" JOIN buildingrenttype brt ON brt.buildingid = b.id "
					+ " JOIN renttype rt ON rt.id = brt.renttypeid ");
		}
	}

	public void queryWhereNormal(BuildingSearchBuilder builder, StringBuilder where) {
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			for (Field item : fields) {
				item.setAccessible(true);
				String fieldName = item.getName();
				if (!fieldName.equals("staffId") && !fieldName.equals("typeCode") && !fieldName.startsWith("area")
						&& !fieldName.startsWith("rentPrice")) {
					String value = item.get(builder) != null ? item.get(builder).toString() : null;
					if (StringUtil.checkString(value)) {
						if (item.getType().getName().equals("java.lang.Long")) {
							where.append(" AND b." + fieldName.toLowerCase() + " = " + value);
						} else if (item.getType().getName().equals("java.lang.Integer")) {
							where.append(" AND b." + fieldName.toLowerCase() + " = " + value);
						} else if (item.getType().getName().equals("java.lang.String")) {
							where.append(" AND b." + fieldName.toLowerCase() + " LIKE '%" + value + "%'");
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void queryWhereSpecial(BuildingSearchBuilder builder, StringBuilder where) {
		Long staffId = builder.getStaffId();
		if (staffId != null) {
			where.append(" AND asm.staffid = " + staffId);
		}
		Long areaFrom = builder.getAreaFrom();
		Long areaTo = builder.getAreaTo();
		if (areaFrom != null) {
			where.append(" AND r.value >= " + areaFrom);
		}
		if (areaTo != null) {
			where.append(" AND r.value <= " + areaTo);
		}
		Long rentPriceFrom = builder.getRentPriceFrom();
		Long rentPriceTo = builder.getRentPriceTo();
		if (rentPriceFrom != null) {
			where.append(" AND b.rentprice >= " + rentPriceFrom);
		}
		if (rentPriceTo != null) {
			where.append(" AND b.rentprice <= " + rentPriceTo);
		}
		// Java 7
//		if (typeCode != null && !typeCode.isEmpty()) {
//			where.append(" AND rt.code IN (");
//			for (int i = 0; i < typeCode.size(); i++) {
//				where.append("'" + typeCode.get(i) + "'");
//				if (i < typeCode.size() - 1) {
//					where.append(", ");
//				}
//			}
//			where.append(") ");
//		}
		// Java 8
		List<String> typeCode = builder.getTypeCode();
		if (typeCode != null && !typeCode.isEmpty()) {
			where.append(" AND (");
			where.append(typeCode.stream().map(item -> " rt.code LIKE '%" + item + "%' ")
					.collect(Collectors.joining(" OR ")));
			where.append(" ) ");
		}
	}

	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder builder) {
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.districtid, b.street, "
				+ "b.ward, b.numberofbasement, b.managername, "
				+ "b.managerphonenumber, b.floorarea, b.rentprice, b.brokeragefee, b.servicefee " + "FROM Building b ");
		queryJoin(builder, sql);

		StringBuilder where = new StringBuilder("WHERE 1 = 1 ");
		queryWhereNormal(builder, where);
		queryWhereSpecial(builder, where);
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
