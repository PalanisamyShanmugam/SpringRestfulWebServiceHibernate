package com.javaspringclub.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.javaspringclub.constants.RestConstants;
import com.javaspringclub.entity.ResponseHeaderDto;
import com.javaspringclub.entity.TaskDto;
import com.javaspringclub.entity.TaskParentDto;
import com.javaspringclub.entity.TaskParentResponseDto;
import com.javaspringclub.helper.JsonTransformers;
import com.javaspringclub.repository.TaskParentRepository;
import com.javaspringclub.repository.TaskRepository;
import com.javaspringclub.service.TaskService;

@RestController
public class TaskController extends RestConstants {

	static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private TaskService taskService;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	TaskParentRepository taskParentRepository;

	JsonTransformers jsonTransformers = new JsonTransformers();

	public TaskController() {

	}

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@RequestMapping(value = "/addTaskInfo/v1", method = RequestMethod.POST)
	public String addTaskInfo(@RequestBody String requestJson) {
		LOGGER.info("Start addTaskInfo TaskController");

		TaskDto taskDto = new TaskDto();
		String responseJson = null;

		try {
			LOGGER.debug("addTaskInfo RequestJson in TaskController: " + requestJson);
			taskDto = (TaskDto) jsonTransformers.JsonToObject(requestJson, taskDto);

			taskService.addTaskInfo(taskDto);

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			TaskParentResponseDto taskResponseDto = new TaskParentResponseDto();
			List<TaskParentDto> taskList = taskParentRepository.getTaskParentInfo();

			taskResponseDto.setTaskInfo(taskList);
			taskResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(taskResponseDto);
			LOGGER.debug("addTaskInfo ResponseJson in TaskController: " + responseJson);

			LOGGER.info("End addTaskInfo TaskController");
		} catch (Exception e) {

		}

		return responseJson;
	}

	@RequestMapping(value = "/updateTaskDetails/v1", method = RequestMethod.POST)
	public String updateTaskDetails(@RequestBody String requestJson) {
		LOGGER.info("Start updateTaskDetails ProjectController");
		TaskDto taskDto = new TaskDto();
		String responseJson = null;

		LOGGER.debug("updateTaskDetails RequestJson in TaskController: " + requestJson);
		try {

			taskDto = (TaskDto) jsonTransformers.JsonToObject(requestJson, taskDto);
			taskService.updateTaskDetails(taskDto);

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			TaskParentResponseDto taskResponseDto = new TaskParentResponseDto();
			List<TaskParentDto> taskList = taskParentRepository.getTaskParentInfo();

			taskResponseDto.setTaskInfo(taskList);
			taskResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(taskResponseDto);
			LOGGER.debug("addTaskInfo ResponseJson in TaskController: " + responseJson);

			LOGGER.info("End updateTaskDetails TaskController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/deleteTaskDetails/v1", method = RequestMethod.POST)
	public String deleteTaskDetails(@RequestBody String requestJson) {
		LOGGER.info("Start deleteTaskDetails TaskController");
		TaskDto taskDto = new TaskDto();
		String responseJson = null;

		LOGGER.debug("deleteTaskDetails RequestJson in TaskController: " + requestJson);
		try {

			taskDto = (TaskDto) jsonTransformers.JsonToObject(requestJson, taskDto);
			taskService.deleteTaskDetails(taskDto.getTask_id());

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			TaskParentResponseDto taskResponseDto = new TaskParentResponseDto();
			List<TaskParentDto> taskList = taskParentRepository.getTaskParentInfo();

			taskResponseDto.setTaskInfo(taskList);
			taskResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(taskResponseDto);
			LOGGER.debug("addTaskInfo ResponseJson in TaskController: " + responseJson);

			LOGGER.info("End deleteTaskDetails TaskController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/getTaskInfo/v1", method = RequestMethod.POST)
	public String getTaskInfo() {
		LOGGER.info("Start getTaskInfo TaskController");
		String responseJson = null;

		try {

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			TaskParentResponseDto taskResponseDto = new TaskParentResponseDto();
			List<TaskParentDto> taskList = taskParentRepository.getTaskParentInfo();

			taskResponseDto.setTaskInfo(taskList);
			taskResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(taskResponseDto);
			LOGGER.debug("getTaskInfo ResponseJson in TaskController: " + responseJson);

			LOGGER.info("End getTaskInfo TaskController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	// Sort Task By Start Date

	@RequestMapping(value = "/getSortTaskByStartDate/v1", method = RequestMethod.POST)
	public String getSortTaskByStartDate() {
		LOGGER.info("Start getSortTaskByStartDate TaskController");
		String responseJson = null;

		try {

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			TaskParentResponseDto taskResponseDto = new TaskParentResponseDto();
			List<TaskParentDto> taskList = taskParentRepository.getSortTaskByStartDate();
			taskResponseDto.setTaskInfo(taskList);
			taskResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(taskResponseDto);
			LOGGER.debug("getSortTaskByStartDate ResponseJson in TaskController: " + responseJson);

			LOGGER.info("End getSortTaskByStartDate TaskController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	// Sort Task By End Date

	@RequestMapping(value = "/getSortTaskByEndDate/v1", method = RequestMethod.POST)
	public String getSortTaskByEndDate() {
		LOGGER.info("Start getSortTaskByEndDate TaskController");
		String responseJson = null;

		try {

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			TaskParentResponseDto taskResponseDto = new TaskParentResponseDto();
			List<TaskParentDto> taskList = taskParentRepository.getSortTaskByEndDate();
			taskResponseDto.setTaskInfo(taskList);
			taskResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(taskResponseDto);
			LOGGER.debug("getSortTaskByEndDate ResponseJson in TaskController: " + responseJson);

			LOGGER.info("End getSortTaskByEndDate TaskController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	// Sort Task By Priority

	@RequestMapping(value = "/getSortTaskByPriority/v1", method = RequestMethod.POST)
	public String getSortTaskByPriority() {
		LOGGER.info("Start getSortTaskByPriority TaskController");
		String responseJson = null;

		try {

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			TaskParentResponseDto taskResponseDto = new TaskParentResponseDto();
			List<TaskParentDto> taskList = taskParentRepository.getSortTaskByPriority();
			taskResponseDto.setTaskInfo(taskList);
			taskResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(taskResponseDto);
			LOGGER.debug("getSortTaskByPriority ResponseJson in TaskController: " + responseJson);

			LOGGER.info("End getSortTaskByPriority TaskController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	// Sort Task By Completed Status

	@RequestMapping(value = "/getSortTaskByCompletedStatus/v1", method = RequestMethod.POST)
	public String getSortTaskByCompletedStatus() {
		LOGGER.info("Start getSortTaskByCompletedStatus TaskController");
		String responseJson = null;

		try {

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			TaskParentResponseDto taskResponseDto = new TaskParentResponseDto();
			List<TaskParentDto> taskList = taskParentRepository.getSortTaskByCompletedStatus();
			taskResponseDto.setTaskInfo(taskList);
			taskResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(taskResponseDto);
			LOGGER.debug("getSortTaskByCompletedStatus ResponseJson in TaskController: " + responseJson);

			LOGGER.info("End getSortTaskByCompletedStatus TaskController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/getTaskDetailsById/v1", method = RequestMethod.POST)
	public String getTaskDetailsById(@RequestBody String requestJson) {
		LOGGER.info("Start getTaskDetailsById TaskController");
		TaskParentDto taskDto = new TaskParentDto();
		String responseJson = null;
		TaskParentResponseDto taskResponseDto = new TaskParentResponseDto();

		LOGGER.debug("getTaskDetailsById RequestJson in TaskController: " + requestJson);
		try {

			JsonTransformers jsonTransformers = new JsonTransformers();
			taskDto = (TaskParentDto) jsonTransformers.JsonToObject(requestJson, taskDto);
			List<TaskParentDto> taskList = taskParentRepository.getTaskDetailsById(taskDto.getProject_id());

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);

			taskResponseDto.setTaskInfo(taskList);
			taskResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(taskResponseDto);
			LOGGER.debug("getTaskDetailsById ResponseJson in TaskController: " + responseJson);

			LOGGER.info("End getTaskDetailsById TaskController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/getTaskDetailsByTask/v1", method = RequestMethod.POST)
	public String getTaskDetailsByTask(@RequestBody String requestJson) {
		LOGGER.info("Start getTaskDetailsByTask TaskController");
		TaskParentDto taskDto = new TaskParentDto();
		String responseJson = null;
		TaskParentResponseDto taskResponseDto = new TaskParentResponseDto();
		List<TaskParentDto> taskList = new ArrayList<TaskParentDto>();

		LOGGER.debug("getTaskDetailsByTask RequestJson in TaskController: " + requestJson);
		try {

			JsonTransformers jsonTransformers = new JsonTransformers();
			taskDto = (TaskParentDto) jsonTransformers.JsonToObject(requestJson, taskDto);
			if (taskDto.getTask() != null) {
				taskList = taskParentRepository.getTaskDetailsByTask(taskDto.getTask());
			} else {
				taskList = taskParentRepository.getTaskParentInfo();
			}

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);

			taskResponseDto.setTaskInfo(taskList);
			taskResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(taskResponseDto);
			LOGGER.debug("getTaskDetailsByTask ResponseJson in TaskController: " + responseJson);

			LOGGER.info("End getTaskDetailsByTask TaskController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/getTaskDetailsByProject/v1", method = RequestMethod.POST)
	public String getTaskDetailsByProject(@RequestBody String requestJson) {
		LOGGER.info("Start getTaskDetailsByTask TaskController");
		TaskParentDto taskDto = new TaskParentDto();
		String responseJson = null;
		TaskParentResponseDto taskResponseDto = new TaskParentResponseDto();
		List<TaskParentDto> taskList = new ArrayList<TaskParentDto>();

		LOGGER.debug("getTaskDetailsByTask RequestJson in TaskController: " + requestJson);
		try {

			JsonTransformers jsonTransformers = new JsonTransformers();
			taskDto = (TaskParentDto) jsonTransformers.JsonToObject(requestJson, taskDto);
			if (taskDto.getTask() != null) {
				taskList = taskParentRepository.getTaskDetailsByProject(taskDto.getTask());
			} else {
				taskList = taskParentRepository.getTaskParentInfo();
			}

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);

			taskResponseDto.setTaskInfo(taskList);
			taskResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(taskResponseDto);
			LOGGER.debug("getTaskDetailsByTask ResponseJson in TaskController: " + responseJson);

			LOGGER.info("End getTaskDetailsByTask TaskController");
		} catch (Exception e) {
		}

		return responseJson;
	}

}
