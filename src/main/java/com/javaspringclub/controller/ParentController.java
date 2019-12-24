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
import com.javaspringclub.entity.ParentDto;
import com.javaspringclub.entity.ParentResponseDto;
import com.javaspringclub.entity.ResponseHeaderDto;
import com.javaspringclub.helper.JsonTransformers;
import com.javaspringclub.repository.ParentRepository;
import com.javaspringclub.service.ParentService;

@RestController
public class ParentController extends RestConstants {

	public static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	private ParentService parentService;

	@Autowired
	private ParentRepository parentRepository;

	JsonTransformers jsonTransformers = new JsonTransformers();

	public ParentController() {

	}

	public ParentController(ParentService parentService) {
		this.parentService = parentService;
	}

	@RequestMapping(value = "/getParentDetails/v1", method = RequestMethod.POST)
	public String getParentDetails() {
		LOGGER.info("Start getTaskDetails ParentController");
		String responseJson = null;

		try {

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			ParentResponseDto parentResponseDto = new ParentResponseDto();
			List<ParentDto> parentInfo = parentService.getParentDetails();
			LOGGER.debug("ParentList from getParentDetails: " + parentInfo.size());
			parentResponseDto.setParentInfo(parentInfo);
			parentResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(parentResponseDto);
			LOGGER.debug("getParentDetails ResponseJson in ParentController: " + responseJson);

			LOGGER.info("End getParentDetails ParentController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/getParentDetailsById/v1", method = RequestMethod.POST)
	public String getParentDetailsById(@RequestBody String requestJson) {
		LOGGER.info("Start getParentDetailsById ParentController");
		ParentDto parentDto = new ParentDto();
		String responseJson = null;
		ParentResponseDto parentResponseDto = new ParentResponseDto();
		List<ParentDto> parentDtoList = null;

		LOGGER.debug("getParentDetailsById RequestJson in ParentController: " + requestJson);
		try {
			parentDto = (ParentDto) jsonTransformers.JsonToObject(requestJson, parentDto);
			parentDto = parentService.getParentDetailsById(parentDto.getParent_id());

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);

			if (parentDto != null) {
				parentDtoList = new ArrayList<ParentDto>();
				parentDtoList.add(parentDto);
				responseHeader.setResponseMessage(RESPONSE_MESSAGE);
			} else {
				responseHeader.setResponseMessage("Parent does not exist");
			}

			parentResponseDto.setParentInfo(parentDtoList);
			parentResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(parentResponseDto);
			LOGGER.debug("getParentDetailsById ResponseJson in ParentController: " + responseJson);

			LOGGER.info("End getParentDetailsById ParentController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/updateParentDetails/v1", method = RequestMethod.POST)
	public String updateParentDetails(@RequestBody String requestJson) {
		LOGGER.info("Start updateParentDetails ParentController");
		ParentDto parentDto = new ParentDto();
		String responseJson = null;
		ParentResponseDto parentResponseDto = new ParentResponseDto();
		List<ParentDto> parentDtoList = new ArrayList<ParentDto>();

		LOGGER.debug("updateParentDetails RequestJson in ParentController: " + requestJson);
		try {

			parentDto = (ParentDto) jsonTransformers.JsonToObject(requestJson, parentDto);
			parentService.updateParentDetails(parentDto);

			parentDtoList = parentService.getParentDetails();
			LOGGER.debug("ParentList in TaskController :" + parentDtoList.size());

			if (parentDtoList != null) {
				for (ParentDto parent : parentDtoList) {
					String taskName = parent.getParent_task();
				}
			}

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);

			if (parentDtoList.size() > 0) {
				parentResponseDto.setParentInfo(parentDtoList);
				responseHeader.setResponseMessage(RESPONSE_MESSAGE);
			} else {
				responseHeader.setResponseMessage("Parent does not exist");
			}
			parentResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(parentResponseDto);
			LOGGER.debug("updateParentDetails ResponseJson in ParentController: " + responseJson);

			LOGGER.info("End updateParentDetails ParentController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/deleteParentDetails/v1", method = RequestMethod.POST)
	public String deleteParentDetails(@RequestBody String requestJson) {
		LOGGER.info("Start deleteParentDetails ParentController");
		ParentDto parentDto = new ParentDto();
		String responseJson = null;
		ParentResponseDto parentResponseDto = new ParentResponseDto();
		List<ParentDto> parentDtoList = new ArrayList<ParentDto>();

		LOGGER.debug("deleteParentDetails RequestJson in ParentController: " + requestJson);
		try {

			parentDto = (ParentDto) jsonTransformers.JsonToObject(requestJson, parentDto);
			parentService.deleteParentDetails(parentDto.getParent_id());

			parentDtoList = parentService.getParentDetails();
			LOGGER.debug("ParentList after delete: " + parentDtoList.size());

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);

			if (parentDtoList.size() > 0) {
				responseHeader.setResponseMessage(RESPONSE_MESSAGE);
				parentResponseDto.setParentInfo(parentDtoList);
			} else {
				responseHeader.setResponseMessage("Task does not exist");
			}

			parentResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(parentResponseDto);
			LOGGER.debug("deleteParentDetails ResponseJson in ParentController: " + responseJson);

			LOGGER.info("End deleteParentDetails ParentController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/addParentInfo/v1", method = RequestMethod.POST)
	public String addParentInfo(@RequestBody String requestJson) {
		LOGGER.info("Start addParentInfo ParentController");

		ParentDto parentDto = new ParentDto();
		String responseJson = null;
		ParentResponseDto parentResponseDto = new ParentResponseDto();
		List<ParentDto> parentDtoList = new ArrayList<ParentDto>();
		ResponseHeaderDto responseHeader = new ResponseHeaderDto();

		try {
			LOGGER.debug("addParentInfo RequestJson in ParentController: " + requestJson);
			parentDto = (ParentDto) jsonTransformers.JsonToObject(requestJson, parentDto);

			parentService.addParentInfo(parentDto);

			parentDtoList = parentService.getParentDetails();

			if (parentDtoList.size() > 0) {
				responseHeader.setResponseMessage(RESPONSE_MESSAGE);
				responseHeader.setStatus(RESPONSE_SUCCESS);
				responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
				parentResponseDto.setParentInfo(parentDtoList);
			} else {
				responseHeader.setResponseMessage("Parent is empty in response");

			}
			parentResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(parentResponseDto);
			LOGGER.debug("addParentInfo responseJson in ParentController: " + responseJson);

			LOGGER.info("End addParentInfo ParentController");
		} catch (Exception e) {

		}

		return responseJson;
	}

	@RequestMapping(value = "/getParentDetailsByTask/v1", method = RequestMethod.POST)
	public String getParentDetailsByTask(@RequestBody String requestJson) {
		LOGGER.info("Start getParentDetailsByTask ParentController");
		ParentDto parentDto = new ParentDto();
		String responseJson = null;
		ParentResponseDto parentResponseDto = new ParentResponseDto();
		List<ParentDto> parentDtoList = null;

		LOGGER.debug("getParentDetailsByTask RequestJson in ParentController: " + requestJson);
		try {
			parentDto = (ParentDto) jsonTransformers.JsonToObject(requestJson, parentDto);

			if (parentDto.getParent_task() != null) {
				parentDtoList = parentRepository.getParentDetailsByTask(parentDto.getParent_task());
			} else {
				parentDtoList = parentService.getParentDetails();
			}

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);

			if (parentDtoList.size() > 0) {
				responseHeader.setResponseMessage(RESPONSE_MESSAGE);
			} else {
				responseHeader.setResponseMessage("Parent does not exist");
			}

			parentResponseDto.setParentInfo(parentDtoList);
			parentResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(parentResponseDto);
			LOGGER.debug("getParentDetailsByTask ResponseJson in ParentController: " + responseJson);

			LOGGER.info("End getParentDetailsByTask ParentController");
		} catch (Exception e) {
		}

		return responseJson;
	}

}
