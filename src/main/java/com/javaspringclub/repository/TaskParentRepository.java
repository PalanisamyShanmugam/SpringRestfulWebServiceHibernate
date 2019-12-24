package com.javaspringclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.javaspringclub.entity.TaskParentDto;

@Repository("taskParentRepository")
public interface TaskParentRepository extends CrudRepository<TaskParentDto, Integer>{
	
	
	static final String GET_TASK_PARENT_INFO = " SELECT T.TASK_ID,T.PARENT_ID,T.PROJECT_ID,T.TASK,T.START_DATE,T.END_DATE,T.PRIORITY,T.STATUS,P.PARENT_TASK FROM TASK T INNER JOIN PARENT P "+
													  " ON T.PARENT_ID = P.PARENT_ID ";
	
	static final String GET_SORT_TASK_BY_START_DATE = " SELECT T.TASK_ID,T.PARENT_ID,T.PROJECT_ID,T.TASK,T.START_DATE,T.END_DATE,T.PRIORITY,T.STATUS,P.PARENT_TASK FROM TASK T INNER JOIN PARENT P "+
														" ON T.PARENT_ID = P.PARENT_ID "+
														" ORDER BY T.START_DATE DESC";
	static final String GET_SORT_TASK_BY_END_DATE = " SELECT T.TASK_ID,T.PARENT_ID,T.PROJECT_ID,T.TASK,T.START_DATE,T.END_DATE,T.PRIORITY,T.STATUS,P.PARENT_TASK FROM TASK T INNER JOIN PARENT P "+
														" ON T.PARENT_ID = P.PARENT_ID "+
														" ORDER BY T.END_DATE DESC";
	static final String GET_SORT_TASK_BY_PRIORITY = " SELECT T.TASK_ID,T.PARENT_ID,T.PROJECT_ID,T.TASK,T.START_DATE,T.END_DATE,T.PRIORITY,T.STATUS,P.PARENT_TASK FROM TASK T INNER JOIN PARENT P "+
														" ON T.PARENT_ID = P.PARENT_ID "+
														" ORDER BY T.PRIORITY DESC";
	static final String GET_SORT_TASK_BY_COMPLETED_STATUS = " SELECT T.TASK_ID,T.PARENT_ID,T.PROJECT_ID,T.TASK,T.START_DATE,T.END_DATE,T.PRIORITY,T.STATUS,P.PARENT_TASK FROM TASK T INNER JOIN PARENT P "+
														" ON T.PARENT_ID = P.PARENT_ID "+
														" WHERE LOWER(T.STATUS) = 'COMPLETED' ";

	static final String GET_TASK_DETAILS_BYID = " SELECT T.TASK_ID,T.PARENT_ID,T.PROJECT_ID,T.TASK,T.START_DATE,T.END_DATE,T.PRIORITY,T.STATUS,P.PARENT_TASK FROM TASK T INNER JOIN PARENT P "+
														" ON T.PARENT_ID = P.PARENT_ID "+
														" WHERE T.PROJECT_ID=:project_id ";
	static final String GET_TASK_DETAILS_BYTASK = " SELECT T.TASK_ID,T.PARENT_ID,T.PROJECT_ID,T.TASK,T.START_DATE,T.END_DATE,T.PRIORITY,T.STATUS,P.PARENT_TASK FROM TASK T INNER JOIN PARENT P "+
														" ON T.PARENT_ID = P.PARENT_ID "+
														" WHERE T.TASK LIKE %:task% ";
	
	static final String GET_TASK_DETAILS_BYPROJECT = " SELECT T.TASK_ID,T.PARENT_ID,T.PROJECT_ID,T.TASK,T.START_DATE,T.END_DATE,T.PRIORITY,T.STATUS,P.PARENT_TASK "+
														" FROM TASK T "+
														" INNER JOIN PARENT P  ON T.PARENT_ID = P.PARENT_ID "+
														" INNER JOIN PROJECT PRO ON T.PROJECT_ID = PRO.PROJECT_ID "+
														" WHERE T.PROJECT_ID = (SELECT PROJECT_ID FROM PROJECT WHERE PROJECT LIKE %:project% )";
	
	@Query(nativeQuery = true, value=GET_TASK_PARENT_INFO)
	public List<TaskParentDto> getTaskParentInfo();
	
	@Query(nativeQuery = true, value=GET_SORT_TASK_BY_START_DATE)
	public List<TaskParentDto> getSortTaskByStartDate();
	
	@Query(nativeQuery = true, value=GET_SORT_TASK_BY_END_DATE)
	public List<TaskParentDto> getSortTaskByEndDate();
	
	@Query(nativeQuery = true, value=GET_SORT_TASK_BY_PRIORITY)
	public List<TaskParentDto> getSortTaskByPriority();
	
	@Query(nativeQuery = true, value=GET_SORT_TASK_BY_COMPLETED_STATUS)
	public List<TaskParentDto> getSortTaskByCompletedStatus();

	@Query(nativeQuery = true, value=GET_TASK_DETAILS_BYID)
	public List<TaskParentDto> getTaskDetailsById(@Param("project_id") int project_id);

	@Query(nativeQuery = true, value=GET_TASK_DETAILS_BYTASK)
	public List<TaskParentDto> getTaskDetailsByTask(@Param("task") String task);

	@Query(nativeQuery = true, value=GET_TASK_DETAILS_BYPROJECT)
	public List<TaskParentDto> getTaskDetailsByProject(@Param("project") String project);
}
