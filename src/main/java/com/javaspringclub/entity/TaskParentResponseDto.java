package com.javaspringclub.entity;

import java.util.List;

public class TaskParentResponseDto {
	
	private ResponseHeaderDto responseHeader;
	private List<TaskParentDto> taskInfo;
	/**
	 * @return the responseHeader
	 */
	public ResponseHeaderDto getResponseHeader() {
		return responseHeader;
	}
	/**
	 * @param responseHeader the responseHeader to set
	 */
	public void setResponseHeader(ResponseHeaderDto responseHeader) {
		this.responseHeader = responseHeader;
	}
	/**
	 * @return the taskInfo
	 */
	public List<TaskParentDto> getTaskInfo() {
		return taskInfo;
	}
	/**
	 * @param taskInfo the taskInfo to set
	 */
	public void setTaskInfo(List<TaskParentDto> taskInfo) {
		this.taskInfo = taskInfo;
	}
	
	
}
