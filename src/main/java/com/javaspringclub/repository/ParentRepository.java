package com.javaspringclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javaspringclub.entity.ParentDto;

@Repository("parentRepository")
public interface ParentRepository extends CrudRepository<ParentDto, Integer>{

	
	String GET_PARENT_DETAILS_BY_TASK = "SELECT * FROM PARENT WHERE PARENT_TASK LIKE %:parent_task%";

	@Query(nativeQuery = true, value=GET_PARENT_DETAILS_BY_TASK)
	public List<ParentDto> getParentDetailsByTask(@Param("parent_task") String parent_task);
	

	
}
