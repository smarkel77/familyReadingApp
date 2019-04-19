package com.techelevator.model;

import java.time.LocalDate;

public class CompetitionRequest {
	
	private long competitionId;
	private String nameOfCompetition;
	private String startDate;
	private String endDate;
	private String description;
	private int minutesToFinish;
	private Long familyId;
	
	public long getCompetitionId() {
		return competitionId;
	}
	public void setCompetitionId(long competitionId) {
		this.competitionId = competitionId;
	}
	public String getNameOfCompetition() {
		return nameOfCompetition;
	}
	public void setNameOfCompetition(String nameOfCompetition) {
		this.nameOfCompetition = nameOfCompetition;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getMinutesToFinish() {
		return minutesToFinish;
	}
	public void setMinutesToFinish(int minutesToFinish) {
		this.minutesToFinish = minutesToFinish;
	}
	public Long getFamilyId() {
		return familyId;
	}
	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}
	
	
	

}
