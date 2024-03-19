package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.utils.ConnectionUtil;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {

	@Override
	public List<Long> getRentAreaById(Long buildingId) {
		String sql = "SELECT rentarea.value FROM rentarea "
				+ "INNER JOIN building ON building.id = rentarea.buildingid "
				+ "WHERE buildingid = " + buildingId;
		
		List<Long> result = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery(sql)) {
			while (rs.next()) {
				result.add(rs.getLong("value"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection failed");
		}
		
		return result;
	}

}
