package com.javaspringclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.javaspringclub.entity.TaskDto;
import com.javaspringclub.entity.TaskParentDto;

@Repository("taskRepository")
public interface TaskRepository extends CrudRepository<TaskDto, Integer>{
	
	static final String GET_SORT_TASK_BY_START_DATE = "SELECT * FROM TASK ORDER BY START_DATE DESC";
	static final String GET_SORT_TASK_BY_END_DATE = "SELECT * FROM TASK ORDER BY END_DATE DESC";
	static final String GET_SORT_TASK_BY_PRIORITY = "SELECT * FROM TASK ORDER BY PRIORITY DESC";
	static final String GET_SORT_TASK_BY_COMPLETED_STATUS = "SELECT * FROM TASK WHERE LOWER(STATUS) = 'COMPLETED' ";
	static final String GET_TASK_PARENT_INFO = " SELECT T.TASK_ID,T.PARENT_ID,T.PROJECT_ID,T.TASK,T.START_DATE,T.END_DATE,T.PRIORITY,T.STATUS,P.PARENT_TASK FROM TASK T INNER JOIN PARENT P "+
												" ON T.PARENT_ID = P.PARENT_ID ";
	
	@Query(nativeQuery = true, value=GET_SORT_TASK_BY_START_DATE)
	List<TaskDto> getSortTaskByStartDate();

	@Query(nativeQuery = true, value=GET_SORT_TASK_BY_END_DATE)
	List<TaskDto> getSortTaskByEndDate();

	@Query(nativeQuery = true, value=GET_SORT_TASK_BY_PRIORITY)
	List<TaskDto> getSortTaskByPriority();
	
	
	@Query(nativeQuery = true, value=GET_SORT_TASK_BY_COMPLETED_STATUS)
	List<TaskDto> getSortTaskByCompletedStatus();
	
	@Query(nativeQuery = true, value=GET_TASK_PARENT_INFO)
	List<TaskParentDto> getTaskParentInfo();
}
