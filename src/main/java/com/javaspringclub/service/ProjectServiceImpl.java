package com.javaspringclub.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaspringclub.entity.Project;
import com.javaspringclub.repository.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService{
	
	@Autowired
	private ProjectRepository projectRepository;
	
	
	@Override
	@Transactional
	public Project getProjectDetailsById(int id) {
		Project project = projectRepository.findById(id).get();
		return project;
	}

	
	@Override
	@Transactional
	public List<Project> getProjectDetails() {
		List<Project> list = new ArrayList<Project>();
		projectRepository.findAll().forEach(e -> list.add(e));
		return list;
	}

	@Override
	@Transactional
	public void addProjectInfo(Project projectDto) {
		projectRepository.save(projectDto);

	}


	@Override
	@Transactional
	public void updateProjectDetails(Project projectDto) {
		Project project = projectRepository.findById(projectDto.getProject_id()).get();
		
		if(project != null){
			projectRepository.save(projectDto);
		}
		
	}
	
	
	@Override
	@Transactional
	public void deleteProjectDetails(int projectId) {
		projectRepository.deleteById(projectId);
		
	}

}
