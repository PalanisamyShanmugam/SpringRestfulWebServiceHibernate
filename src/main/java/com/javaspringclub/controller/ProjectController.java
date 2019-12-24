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
import com.javaspringclub.entity.Project;
import com.javaspringclub.entity.ProjectCompletedTasks;
import com.javaspringclub.entity.ProjectDto;
import com.javaspringclub.entity.ProjectInfoResponseDto;
import com.javaspringclub.entity.ProjectResponseDto;
import com.javaspringclub.entity.ProjectTasks;
import com.javaspringclub.entity.ResponseHeaderDto;
import com.javaspringclub.helper.JsonTransformers;
import com.javaspringclub.repository.ProjectCompletedTasksRepository;
import com.javaspringclub.repository.ProjectInfoRepository;
import com.javaspringclub.repository.ProjectRepository;
import com.javaspringclub.service.ProjectService;

@RestController
public class ProjectController extends RestConstants {

	static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	private ProjectService projectService;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	ProjectCompletedTasksRepository projectCompletedTasksRepository;

	@Autowired
	ProjectInfoRepository projectInfoRepository;

	JsonTransformers jsonTransformers = new JsonTransformers();

	public ProjectController() {

	}

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@RequestMapping(value = "/addProjectInfo/v1", method = RequestMethod.POST)
	public String addProjectInfo(@RequestBody String requestJson) {
		LOGGER.info("Start addProjectInfo ProjectController");

		String responseJson = null;
		Project project1 = new Project();
		ResponseHeaderDto responseHeader = new ResponseHeaderDto();
		ProjectResponseDto projectResponseDto = new ProjectResponseDto();
		List<Project> projectList = new ArrayList<Project>();
		List<ProjectDto> projectDtoList = new ArrayList<ProjectDto>();

		try {
			LOGGER.debug("addProjectInfo RequestJson in ProjectController: " + requestJson);
			project1 = (Project) jsonTransformers.JsonToObject(requestJson, project1);
			projectService.addProjectInfo(project1);
			
			projectList = projectService.getProjectDetails();

			// Response Processing.
						ProjectInfoResponseDto projectInfoResponseDto = new ProjectInfoResponseDto();
						for (Project project : projectList) {

							ProjectDto projectDto = new ProjectDto();
							projectDto.setProject_id(project.getProject_id());
							projectDto.setProject(project.getProject());
							projectDto.setStart_date(project.getStart_date());
							projectDto.setEnd_date(project.getEnd_date());
							projectDto.setPriority(project.getPriority());

							ProjectTasks projectTasks = projectInfoRepository.getNoOfTasks(project.getProject_id());
							if (projectTasks != null && projectTasks.getNoOfTasks() > 0) {
								projectDto.setNoOfTasks(projectTasks.getNoOfTasks());
							} else {
								projectDto.setNoOfTasks(0);
							}

							ProjectCompletedTasks projectCompletedTasks = projectCompletedTasksRepository.getCompletedTasks(project.getProject_id());
							if (projectCompletedTasks != null && projectCompletedTasks.getCompletedTasks() > 0) {
								projectDto.setCompletedTasks(projectCompletedTasks.getCompletedTasks());
							} else {
								projectDto.setCompletedTasks(0);
							}

							projectDtoList.add(projectDto);

						}
						projectInfoResponseDto.setProjectInfo(projectDtoList);
						projectInfoResponseDto.setResponseHeader(responseHeader);

						responseJson = jsonTransformers.ObjectToJson(projectInfoResponseDto);
						LOGGER.debug("getProjectInfo ResponseJson in ProjectController: " + responseJson);

			LOGGER.info("End addProjectInfo ProjectController");
		} catch (Exception e) {

		}

		return responseJson;
	}

	@RequestMapping(value = "/getProjectDetailsById/v1", method = RequestMethod.POST)
	public String getProjectDetailsById(@RequestBody String requestJson) {
		LOGGER.info("Start getProjectDetailsById ProjectController");
		Project projectDto = new Project();
		String responseJson = null;
		ProjectResponseDto projectResponseDto = new ProjectResponseDto();
		List<Project> projectDtoList = null;

		LOGGER.debug("getProjectDetailsById RequestJson in ProjectController: " + requestJson);
		try {

			JsonTransformers jsonTransformers = new JsonTransformers();
			projectDto = (Project) jsonTransformers.JsonToObject(requestJson, projectDto);
			projectDto = projectService.getProjectDetailsById(projectDto.getProject_id());

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);

			if (projectDto != null) {
				projectDtoList = new ArrayList<Project>();
				projectDtoList.add(projectDto);
				responseHeader.setResponseMessage(RESPONSE_MESSAGE);
			} else {
				responseHeader.setResponseMessage("Project does not exist");
			}

			projectResponseDto.setProjectInfo(projectDtoList);
			projectResponseDto.setResponseHeader(responseHeader);
			responseJson = jsonTransformers.ObjectToJson(projectResponseDto);
			LOGGER.debug("getProjectDetailsById ResponseJson in ProjectController: " + responseJson);

			LOGGER.info("End getProjectDetailsById ProjectController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/getProjectDetails/v1", method = RequestMethod.POST)
	public String getProjectDetails() {
		LOGGER.info("Start getProjectDetails CallUpdate ProjectController");
		String responseJson = null;

		try {
			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			ProjectResponseDto projectResponseDto = new ProjectResponseDto();
			List<Project> projectList = projectService.getProjectDetails();
			projectResponseDto.setProjectInfo(projectList);
			projectResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(projectResponseDto);
			LOGGER.debug("getProjectDetails ResponseJson in ProjectController: " + responseJson);

			LOGGER.info("End getProjectDetails ProjectController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/updateProjectDetails/v1", method = RequestMethod.POST)
	public String updateProjectDetails(@RequestBody String requestJson) {
		LOGGER.info("Start updateProjectDetails ProjectController");
		Project projectDto = new Project();
		String responseJson = null;
		ProjectResponseDto projectResponseDto = new ProjectResponseDto();
		List<Project> projectDtoList = new ArrayList<Project>();

		LOGGER.debug("getProjectDetailsById RequestJson in ProjectController: " + requestJson);
		try {

			JsonTransformers jsonTransformers = new JsonTransformers();
			projectDto = (Project) jsonTransformers.JsonToObject(requestJson, projectDto);
			projectService.updateProjectDetails(projectDto);

			projectDtoList = projectService.getProjectDetails();

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);

			if (projectDtoList.size() > 0) {
				projectResponseDto.setProjectInfo(projectDtoList);
				responseHeader.setResponseMessage(RESPONSE_MESSAGE);
			} else {
				responseHeader.setResponseMessage("Project does not exist");
			}
			projectResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(projectResponseDto);
			LOGGER.debug("updateProjectDetails ResponseJson in ProjectController: " + responseJson);

			LOGGER.info("End updateProjectDetails ProjectController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/deleteProjectDetails/v1", method = RequestMethod.POST)
	public String deleteProjectDetails(@RequestBody String requestJson) {
		LOGGER.info("Start deleteProjectDetails ProjectController");
		Project projectDto = new Project();
		String responseJson = null;
		ProjectResponseDto projectResponseDto = new ProjectResponseDto();

		LOGGER.debug("deleteProjectDetails RequestJson in ProjectController: " + requestJson);
		try {

			JsonTransformers jsonTransformers = new JsonTransformers();
			projectDto = (Project) jsonTransformers.JsonToObject(requestJson, projectDto);
			projectService.deleteProjectDetails(projectDto.getProject_id());

			List<Project> projectDtoList = new ArrayList<Project>();
			projectDtoList = projectService.getProjectDetails();

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);

			if (projectDtoList.size() > 0) {
				responseHeader.setResponseMessage(RESPONSE_MESSAGE);
				projectResponseDto.setProjectInfo(projectDtoList);
			} else {
				responseHeader.setResponseMessage("Project does not exist");
			}

			projectResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(projectResponseDto);
			LOGGER.debug("deleteProjectDetails ResponseJson in ProjectController: " + responseJson);

			LOGGER.info("End deleteProjectDetails ProjectController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/getProjectInfo/v1", method = RequestMethod.POST)
	public String getProjectInfo() {
		LOGGER.info("Start getProjectInfo ProjectController");
		String responseJson = null;

		List<ProjectDto> projectDtoList = new ArrayList<ProjectDto>();

		try {

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			List<Project> projectList = projectService.getProjectDetails();

			// Response Processing.
			ProjectInfoResponseDto projectInfoResponseDto = new ProjectInfoResponseDto();
			for (Project project : projectList) {

				ProjectDto projectDto = new ProjectDto();
				projectDto.setProject_id(project.getProject_id());
				projectDto.setProject(project.getProject());
				projectDto.setStart_date(project.getStart_date());
				projectDto.setEnd_date(project.getEnd_date());
				projectDto.setPriority(project.getPriority());

				ProjectTasks projectTasks = projectInfoRepository.getNoOfTasks(project.getProject_id());
				if (projectTasks != null && projectTasks.getNoOfTasks() > 0) {
					projectDto.setNoOfTasks(projectTasks.getNoOfTasks());
				} else {
					projectDto.setNoOfTasks(0);
				}

				ProjectCompletedTasks projectCompletedTasks = projectCompletedTasksRepository
						.getCompletedTasks(project.getProject_id());
				if (projectCompletedTasks != null && projectCompletedTasks.getCompletedTasks() > 0) {
					projectDto.setCompletedTasks(projectCompletedTasks.getCompletedTasks());
				} else {
					projectDto.setCompletedTasks(0);
				}

				projectDtoList.add(projectDto);

			}
			projectInfoResponseDto.setProjectInfo(projectDtoList);
			projectInfoResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(projectInfoResponseDto);
			LOGGER.debug("getProjectInfo ResponseJson in ProjectController: " + responseJson);

			LOGGER.info("End getProjectInfo ProjectController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/getSortProjectByStartDate/v1", method = RequestMethod.POST)
	public String getSortProjectByStartDate() {
		LOGGER.info("Start getSortProjectByStartDate ProjectController");
		String responseJson = null;
		List<ProjectDto> projectDtoList = new ArrayList<ProjectDto>();

		try {
			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			List<Project> projectList = projectRepository.getSortProjectByStartDate();
			ProjectInfoResponseDto projectInfoResponseDto = new ProjectInfoResponseDto();

			for (Project project : projectList) {

				ProjectDto projectDto = new ProjectDto();
				projectDto.setProject_id(project.getProject_id());
				projectDto.setProject(project.getProject());
				projectDto.setStart_date(project.getStart_date());
				projectDto.setEnd_date(project.getEnd_date());
				projectDto.setPriority(project.getPriority());

				ProjectTasks projectTasks = projectInfoRepository.getNoOfTasks(project.getProject_id());
				if (projectTasks != null && projectTasks.getNoOfTasks() > 0) {
					projectDto.setNoOfTasks(projectTasks.getNoOfTasks());
				} else {
					projectDto.setNoOfTasks(0);
				}

				ProjectCompletedTasks projectCompletedTasks = projectCompletedTasksRepository
						.getCompletedTasks(project.getProject_id());
				if (projectCompletedTasks != null && projectCompletedTasks.getCompletedTasks() > 0) {
					projectDto.setCompletedTasks(projectCompletedTasks.getCompletedTasks());
				} else {
					projectDto.setCompletedTasks(0);
				}

				projectDtoList.add(projectDto);

			}
			projectInfoResponseDto.setProjectInfo(projectDtoList);
			projectInfoResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(projectInfoResponseDto);
			LOGGER.debug("getSortProjectByStartDate ResponseJson in ProjectController: " + responseJson);

			LOGGER.info("End getSortProjectByStartDate ProjectController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/getSortProjectByEndDate/v1", method = RequestMethod.POST)
	public String getSortProjectByEndDate() {
		LOGGER.info("Start getSortProjectByEndDate ProjectController");
		String responseJson = null;

		List<ProjectDto> projectDtoList = new ArrayList<ProjectDto>();

		try {
			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			List<Project> projectList = projectRepository.getSortProjectByEndDate();
			ProjectInfoResponseDto projectInfoResponseDto = new ProjectInfoResponseDto();

			for (Project project : projectList) {
				ProjectDto projectDto = new ProjectDto();
				projectDto.setProject_id(project.getProject_id());
				projectDto.setProject(project.getProject());
				projectDto.setStart_date(project.getStart_date());
				projectDto.setEnd_date(project.getEnd_date());
				projectDto.setPriority(project.getPriority());

				ProjectTasks projectTasks = projectInfoRepository.getNoOfTasks(project.getProject_id());
				if (projectTasks != null && projectTasks.getNoOfTasks() > 0) {
					projectDto.setNoOfTasks(projectTasks.getNoOfTasks());
				} else {
					projectDto.setNoOfTasks(0);
				}

				ProjectCompletedTasks projectCompletedTasks = projectCompletedTasksRepository
						.getCompletedTasks(project.getProject_id());
				if (projectCompletedTasks != null && projectCompletedTasks.getCompletedTasks() > 0) {
					projectDto.setCompletedTasks(projectCompletedTasks.getCompletedTasks());
				} else {
					projectDto.setCompletedTasks(0);
				}

				projectDtoList.add(projectDto);

			}
			projectInfoResponseDto.setProjectInfo(projectDtoList);
			projectInfoResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(projectInfoResponseDto);
			LOGGER.debug("getSortProjectByEndDate ResponseJson in ProjectController: " + responseJson);

			LOGGER.info("End getSortProjectByEndDate ProjectController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/getSortProjectByPriority/v1", method = RequestMethod.POST)
	public String getSortProjectByPriority() {
		LOGGER.info("Start getSortProjectByPriority ProjectController");
		String responseJson = null;
		List<ProjectDto> projectDtoList = new ArrayList<ProjectDto>();

		try {

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			List<Project> projectList = projectRepository.getSortProjectByPriority();
			ProjectInfoResponseDto projectInfoResponseDto = new ProjectInfoResponseDto();

			for (Project project : projectList) {

				ProjectDto projectDto = new ProjectDto();
				projectDto.setProject_id(project.getProject_id());
				projectDto.setProject(project.getProject());
				projectDto.setStart_date(project.getStart_date());
				projectDto.setEnd_date(project.getEnd_date());
				projectDto.setPriority(project.getPriority());

				ProjectTasks projectTasks = projectInfoRepository.getNoOfTasks(project.getProject_id());
				if (projectTasks != null && projectTasks.getNoOfTasks() > 0) {
					projectDto.setNoOfTasks(projectTasks.getNoOfTasks());
				} else {
					projectDto.setNoOfTasks(0);
				}

				ProjectCompletedTasks projectCompletedTasks = projectCompletedTasksRepository
						.getCompletedTasks(project.getProject_id());
				if (projectCompletedTasks != null && projectCompletedTasks.getCompletedTasks() > 0) {
					projectDto.setCompletedTasks(projectCompletedTasks.getCompletedTasks());
				} else {
					projectDto.setCompletedTasks(0);
				}

				projectDtoList.add(projectDto);

			}
			projectInfoResponseDto.setProjectInfo(projectDtoList);
			projectInfoResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(projectInfoResponseDto);
			LOGGER.debug("getSortProjectByPriority ResponseJson in ProjectController: " + responseJson);
			LOGGER.info("End getSortProjectByPriority ProjectController");
		} catch (Exception e) {
		}

		return responseJson;
	}

	@RequestMapping(value = "/getProjectDetailsByProject/v1", method = RequestMethod.POST)
	public String getProjectDetailsByProject(@RequestBody String requestJson) {
		LOGGER.info("Start getProjectDetailsByProject ProjectController");
		Project projectInfo = new Project();
		String responseJson = null;
		List<ProjectDto> projectDtoList = new ArrayList<ProjectDto>();

		List<Project> projectList = new ArrayList<Project>();
		LOGGER.debug("getProjectDetailsByProject RequestJson in ProjectController: " + requestJson);
		try {

			JsonTransformers jsonTransformers = new JsonTransformers();
			projectInfo = (Project) jsonTransformers.JsonToObject(requestJson, projectInfo);

			if (projectInfo.getProject() != null) {
				projectList = projectRepository.getProjectDetailsByProject(projectInfo.getProject());
			} else {
				projectList = projectService.getProjectDetails();
			}

			ResponseHeaderDto responseHeader = new ResponseHeaderDto();
			responseHeader.setStatus(RESPONSE_SUCCESS);
			responseHeader.setResponseCode(RESPONSE_CODE_SUCCESS);
			responseHeader.setResponseMessage(RESPONSE_MESSAGE);

			ProjectInfoResponseDto projectInfoResponseDto = new ProjectInfoResponseDto();

			for (Project project : projectList) {

				ProjectDto projectDto = new ProjectDto();
				projectDto.setProject_id(project.getProject_id());
				projectDto.setProject(project.getProject());
				projectDto.setStart_date(project.getStart_date());
				projectDto.setEnd_date(project.getEnd_date());
				projectDto.setPriority(project.getPriority());

				ProjectTasks projectTasks = projectInfoRepository.getNoOfTasks(project.getProject_id());
				if (projectTasks != null && projectTasks.getNoOfTasks() > 0) {
					projectDto.setNoOfTasks(projectTasks.getNoOfTasks());
				} else {
					projectDto.setNoOfTasks(0);
				}

				ProjectCompletedTasks projectCompletedTasks = projectCompletedTasksRepository
						.getCompletedTasks(project.getProject_id());
				if (projectCompletedTasks != null && projectCompletedTasks.getCompletedTasks() > 0) {
					projectDto.setCompletedTasks(projectCompletedTasks.getCompletedTasks());
				} else {
					projectDto.setCompletedTasks(0);
				}

				projectDtoList.add(projectDto);

			}
			projectInfoResponseDto.setProjectInfo(projectDtoList);
			projectInfoResponseDto.setResponseHeader(responseHeader);

			responseJson = jsonTransformers.ObjectToJson(projectInfoResponseDto);
			LOGGER.debug("getSortProjectByPriority ResponseJson in ProjectController: " + responseJson);

			LOGGER.info("End getProjectDetailsById ProjectController");
		} catch (Exception e) {
		}

		return responseJson;
	}

}
