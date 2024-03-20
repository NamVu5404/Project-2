package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.DistrictRepository;
import com.javaweb.utils.ConnectionUtil;

@Repository
public class DistrictRepositoryImpl implements DistrictRepository {

	@Override
	public String getDistrictNameByBuildingId(Long buildingId) {
		String sql = "SELECT district.name FROM district "
				+ "INNER JOIN building on building.districtid = district.id "
				+ "WHERE building.id = " + buildingId;

		String result = "";
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(sql)) {
			while (rs.next()) {
				result = rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection failed");
		}

		return result;
	}

}
