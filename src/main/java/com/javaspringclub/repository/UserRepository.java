package com.javaspringclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javaspringclub.entity.UserDto;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<UserDto, Integer>{
	
	static final String GET_SORT_USER_BY_FIRST_NAME = "SELECT * FROM USER ORDER BY FIRST_NAME ASC";
	static final String GET_SORT_USER_BY_LAST_NAME = "SELECT * FROM USER ORDER BY LAST_NAME ASC";
	static final String GET_SORT_USER_BY_EMPLOYEE_ID = "SELECT * FROM USER ORDER BY EMPLOYEE_ID ASC";
	static final String SEARCH_USER_BY_FIRST_LASTNAME = "SELECT * FROM USER WHERE FIRST_NAME LIKE %:first_name% OR LAST_NAME LIKE %:last_name% ";
	
	@Query(nativeQuery = true, value=GET_SORT_USER_BY_FIRST_NAME)
	List<UserDto> getSortUserByFirstName();

	@Query(nativeQuery = true, value=GET_SORT_USER_BY_LAST_NAME)
	List<UserDto> getSortUserByLastName();

	@Query(nativeQuery = true, value=GET_SORT_USER_BY_EMPLOYEE_ID)
	List<UserDto> getSortUserByEmployeeId();
	
	@Query(nativeQuery = true, value=SEARCH_USER_BY_FIRST_LASTNAME)
	List<UserDto> searchUserByFirstLastName(@Param("first_name") String first_name, @Param("last_name") String last_name);
	
	
}
