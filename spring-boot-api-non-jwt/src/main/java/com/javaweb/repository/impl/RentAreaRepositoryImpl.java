package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.RentAreaEntity;
import com.javaweb.utils.ConnectionUtil;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {

	@Override
	public List<String> findByBuildingId(Long buildingId) {
		
		String sql = "SELECT * FROM rentarea WHERE buildingid = " + buildingId;
		List<String> rentAreas = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(sql)) {
			while (rs.next()) {
				RentAreaEntity rentAreaEntity = new RentAreaEntity();
				rentAreaEntity.setValue(rs.getLong("value"));
				rentAreas.add(rentAreaEntity.getValue().toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection failed");
		}
		
		return rentAreas;
	}

}
