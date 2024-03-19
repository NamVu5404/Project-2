package com.javaweb.repository;

import java.util.List;

public interface RentAreaRepository {
	List<Long> getRentAreaById(Long buildingId);
}
