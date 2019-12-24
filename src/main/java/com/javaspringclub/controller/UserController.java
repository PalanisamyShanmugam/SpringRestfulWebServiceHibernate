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
import com.javaspringclub.entity.UserDto;
import com.javaspringclub.entity.UserResponseDto;
import com.javaspringclub.helper.JsonTransformers;
import com.javaspringclub.repository.UserRepository;
import com.javaspringclub.service.UserService;

@RestController
public class UserController extends RestConstants {

	static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	UserRepository userRepository;

	JsonTransformers jsonTransformers = new JsonTransformers();

	public UserController() {

	}

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/getUserDetails/v1", method = RequestMethod.POST)
	public String getUserDetails() {
		LOGGER.info("Start getUserDetails UserController");
		String responseJson = null;

		try {

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			UserResponseDto userResponseDto = new UserResponseDto();
			List<UserDto> userList = userService.getUserDetails();
			LOGGER.debug("userList from getUserDetails: " + userList.size());
			if (userList.size() > 0) {
				userResponseDto.setUserInfo(userList);
			}
			userResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(userResponseDto);
			LOGGER.debug("getUserDetails ResponseJson in UserController: " + responseJson);

			LOGGER.info("End getUserDetails UserController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/getUserDetailsById/v1", method = RequestMethod.POST)
	public String getUserDetailsById(@RequestBody String requestJson) {
		LOGGER.info("Start getUserDetailsById UserController");
		UserDto userDto = new UserDto();
		String responseJson = null;
		UserResponseDto userResponseDto = new UserResponseDto();
		List<UserDto> userDtoList = null;
		try {
			LOGGER.debug("getUserDetailsById RequestJson in UserController: " + requestJson);
			userDto = (UserDto) jsonTransformers.JsonToObject(requestJson, userDto);
			userDto = userService.getUserDetailsById(userDto.getUser_id());

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);

			if (userDto != null) {
				userDtoList = new ArrayList<UserDto>();
				userDtoList.add(userDto);
				responseHeader.setResponseMessage(RESPONSE_MESSAGE);
			} else {
				responseHeader.setResponseMessage("User does not exist");
			}

			userResponseDto.setUserInfo(userDtoList);
			userResponseDto.setResponseHeader(responseHeader);
			responseJson = jsonTransformers.ObjectToJson(userResponseDto);
			LOGGER.debug("getUserDetailsById ResponseJson in UserController: " + responseJson);

			LOGGER.info("End getUserDetailsById UserController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/updateUserDetails/v1", method = RequestMethod.POST)
	public String updateUserDetails(@RequestBody String requestJson) {
		LOGGER.info("Start updateUserDetails UserController");
		UserDto userDto = new UserDto();
		String responseJson = null;
		UserResponseDto userResponseDto = new UserResponseDto();
		List<UserDto> userDtoList = new ArrayList<UserDto>();
		LOGGER.debug("updateUserDetails RequestJson in UserController: " + requestJson);
		try {
			userDto = (UserDto) jsonTransformers.JsonToObject(requestJson, userDto);
			userService.updateUserDetails(userDto);

			userDtoList = userService.getUserDetails();
			LOGGER.debug("UserList in TaskController :" + userDtoList.size());

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);

			if (userDtoList.size() > 0) {
				userResponseDto.setUserInfo(userDtoList);
				responseHeader.setResponseMessage(RESPONSE_MESSAGE);
			} else {
				responseHeader.setResponseMessage("User does not exist");
			}
			userResponseDto.setResponseHeader(responseHeader);
			responseJson = jsonTransformers.ObjectToJson(userResponseDto);
			LOGGER.debug("updateUserDetails ResponseJson in UserController: " + responseJson);

			LOGGER.info("End updateUserDetails UserController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/deleteUserDetails/v1", method = RequestMethod.POST)
	public String deleteUserDetails(@RequestBody String requestJson) {
		LOGGER.info("Start deleteUserDetails UserController");
		UserDto userDto = new UserDto();
		String responseJson = null;
		UserResponseDto userResponseDto = new UserResponseDto();
		List<UserDto> userDtoList = new ArrayList<UserDto>();
		LOGGER.debug("deleteUserDetails RequestJson in UserController: " + requestJson);
		try {
			userDto = (UserDto) jsonTransformers.JsonToObject(requestJson, userDto);
			userService.deleteUserDetails(userDto.getUser_id());

			userDtoList = userService.getUserDetails();
			LOGGER.debug("UserList after delete: " + userDtoList.size());

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			if (userDtoList.size() > 0) {
				responseHeader.setResponseMessage(RESPONSE_MESSAGE);
				userResponseDto.setUserInfo(userDtoList);
			} else {
				responseHeader.setResponseMessage("User does not exist");
			}

			userResponseDto.setResponseHeader(responseHeader);
			responseJson = jsonTransformers.ObjectToJson(userResponseDto);
			LOGGER.debug("deleteUserDetails ResponseJson in UserController: " + responseJson);
			LOGGER.info("End deleteUserDetails UserController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	// Add User Details

	@RequestMapping(value = "/addUserInfo/v1", method = RequestMethod.POST)
	public String addUserInfo(@RequestBody String requestJson) {
		LOGGER.info("Start addUserInfo in UserController");

		UserDto userDto = new UserDto();
		String responseJson = null;
		UserResponseDto userResponseDto = new UserResponseDto();
		List<UserDto> userDtoList = new ArrayList<UserDto>();
		ResponseHeaderDto responseHeader = new ResponseHeaderDto();
		try {
			LOGGER.debug("addUserInfo RequestJson in UserController: " + requestJson);
			userDto = (UserDto) jsonTransformers.JsonToObject(requestJson, userDto);

			userService.addUserInfo(userDto);
			LOGGER.info("UserInfo Saved successfully.");
			userDtoList = userService.getUserDetails();
			if (userDtoList.size() > 0) {
				responseHeader.setResponseMessage(RESPONSE_MESSAGE);
				responseHeader.setStatus(RESPONSE_SUCCESS);
				responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
				userResponseDto.setUserInfo(userDtoList);
			} else {
				responseHeader.setResponseMessage("User is empty in response");
			}
			userResponseDto.setResponseHeader(responseHeader);
			responseJson = jsonTransformers.ObjectToJson(userResponseDto);
			LOGGER.debug("addUserInfo responseJson in UserController: " + responseJson);
			LOGGER.info("End addUserInfo UserController");
		} catch (Exception e) {

		}

		return responseJson;
	}

	// Sort User By FirstName
	@RequestMapping(value = "/getSortUserByFirstName/v1", method = RequestMethod.POST)
	public String getSortUserByFirstName() {
		LOGGER.info("Start getSortUserByFirstName UserController");
		String responseJson = null;
		try {
			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			UserResponseDto userResponseDto = new UserResponseDto();
			List<UserDto> userList = userRepository.getSortUserByFirstName();
			if (userList.size() > 0) {
				userResponseDto.setUserInfo(userList);
			}

			userResponseDto.setResponseHeader(responseHeader);
			responseJson = jsonTransformers.ObjectToJson(userResponseDto);
			LOGGER.debug("getSortUserByFirstName ResponseJson in UserController: " + responseJson);
			LOGGER.info("End getSortUserByFirstName UserController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	// Sort User By LastName

	@RequestMapping(value = "/getSortUserByLastName/v1", method = RequestMethod.POST)
	public String getSortUserByLastName() {
		LOGGER.info("Start getSortUserByLastName UserController");
		String responseJson = null;
		try {
			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			UserResponseDto userResponseDto = new UserResponseDto();
			List<UserDto> userList = userRepository.getSortUserByLastName();
			if (userList.size() > 0) {
				userResponseDto.setUserInfo(userList);
			}
			userResponseDto.setResponseHeader(responseHeader);
			responseJson = jsonTransformers.ObjectToJson(userResponseDto);
			LOGGER.debug("getSortUserByLastName ResponseJson in UserController: " + responseJson);
			LOGGER.info("End getSortUserByLastName UserController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	// Sort User By EmployeeId

	@RequestMapping(value = "/getSortUserByEmployeeId/v1", method = RequestMethod.POST)
	public String getSortUserByEmployeeId() {
		LOGGER.info("Start getSortUserByEmployeeId UserController");
		String responseJson = null;
		try {
			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			UserResponseDto userResponseDto = new UserResponseDto();
			List<UserDto> userList = userRepository.getSortUserByEmployeeId();
			if (userList.size() > 0) {
				userResponseDto.setUserInfo(userList);
			}
			userResponseDto.setResponseHeader(responseHeader);
			responseJson = jsonTransformers.ObjectToJson(userResponseDto);
			LOGGER.debug("getSortUserByEmployeeId ResponseJson in UserController: " + responseJson);

			LOGGER.info("End getSortUserByEmployeeId UserController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	// Search By FirstName or LastName

	@RequestMapping(value = "/searchUserByFirstLastName/v1", method = RequestMethod.POST)
	public String searchUserByFirstLastName(@RequestBody String requestJson) {
		LOGGER.info("Start searchUserByFirstLastName UserController");
		String responseJson = null;
		UserDto userDto = new UserDto();
		UserResponseDto userResponseDto = new UserResponseDto();
		List<UserDto> userList = new ArrayList<UserDto>();
		try {
			userDto = (UserDto) jsonTransformers.JsonToObject(requestJson, userDto);
			if (userDto.getFirst_name() != null && userDto.getLast_name() != null) {
				userList = userRepository.searchUserByFirstLastName(userDto.getFirst_name(), userDto.getLast_name());
			} else {
				userList = userService.getUserDetails();
			}
			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			if (userList.size() > 0) {
				userResponseDto.setUserInfo(userList);
			}
			userResponseDto.setResponseHeader(responseHeader);
			responseJson = jsonTransformers.ObjectToJson(userResponseDto);
			LOGGER.debug("searchUserByFirstLastName ResponseJson in UserController: " + responseJson);
			LOGGER.info("End searchUserByFirstLastName UserController");
		} catch (Exception e) {
		}

		return responseJson;
	}

}
