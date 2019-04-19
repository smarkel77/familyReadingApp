package com.techelevator.model.DAO.JDBC;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.techelevator.model.Competition;
import com.techelevator.model.Person;
import com.techelevator.model.DAO.CompetitionDAO;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCCompetitionDAO implements CompetitionDAO{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JDBCCompetitionDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void addPersonToCompetition(Competition newCompetition, long personId) {
		String query="INSERT INTO competition_people (people_id, competition_id) VALUES (?,?)";
		jdbcTemplate.update(query, personId, newCompetition.getCompetitionId());
	}

	@Override
	public void createNewCompetition(Competition newComp) {
		System.out.println(newComp.getNameOfCompetition() + " " + newComp.getStartDate() + " " + newComp.getEndDate() + " " + newComp.getMinutesToFinish() + " " + newComp.getFamilyId() + " " + newComp.getDescription());
		String insertNewCompetition= "INSERT INTO competition (name_of_competition, start_date, end_date, description, minutes_to_finish, family_id) VALUES (?,?,?,?,?,?);";
		jdbcTemplate.update(insertNewCompetition, newComp.getNameOfCompetition(), newComp.getStartDate(), newComp.getEndDate(), newComp.getDescription(), newComp.getMinutesToFinish(), newComp.getFamilyId());
	}

	@Override
	public List<Competition> getListOfFinishedCompetitions(LocalDate todayDate) {
		List<Competition> finishedCompetitions = new ArrayList<Competition>();
		String searchForFinishedCompetitions= "SELECT * FROM competition WHERE end_date < ?";
		SqlRowSet results=jdbcTemplate.queryForRowSet(searchForFinishedCompetitions, todayDate);
		while (results.next()) {
			Competition competition = mapRowToCompetition(results);
			finishedCompetitions.add(competition);
		}
		return finishedCompetitions;
	}

	@Override
	public List<Competition> getListOfActiveCompetitionsByFamily(LocalDate todayDate, long familyId) {
		List<Competition> activeCompetitions = new ArrayList<Competition>();
		todayDate =  LocalDate.now();
		String getSqlOfActiveCompetitions = "SELECT DISTINCT competition.* FROM competition WHERE family_id = ? AND end_date > ?";
		SqlRowSet results=jdbcTemplate.queryForRowSet(getSqlOfActiveCompetitions, familyId, todayDate);
		while (results.next()) {
			Competition competition = mapRowToCompetition(results);
			activeCompetitions.add(competition);
		}
		return activeCompetitions;
	}

	@Override
	public List<Person> getListOfPeopleInCompetition(long competitionId) {
		List<Person> peopleInCompetitions = new ArrayList<Person>();
		String getSqlOfPeopleInCompetitions = "SELECT * FROM people JOIN people_id FROM competition_people ON people.people_id=competition.people_id WHERE competition_id = ?";
		SqlRowSet results=jdbcTemplate.queryForRowSet(getSqlOfPeopleInCompetitions, competitionId);
		while (results.next()) {
			Person people = mapRowToPerson(results);
			peopleInCompetitions.add(people);
		}
		return peopleInCompetitions;
	}

	@Override
	public List<Competition> getListOfCompetitionsByPerson(LocalDate today, long personId) {
		List<Competition> competitionsByPerson = new ArrayList<Competition>();
		String getSqlOfCompetitionsByPeople = "SELECT * FROM competition JOIN competition_people ON competition.competition_id=competition_people.competition_id WHERE people_id = ? AND competition.end_date > ?";
		SqlRowSet results=jdbcTemplate.queryForRowSet(getSqlOfCompetitionsByPeople, personId, today);
		while (results.next()) {
			Competition competition = mapRowToCompetition(results);
			competitionsByPerson.add(competition);
		}
		return competitionsByPerson;
	}

	@Override
	public void updateCompetition(Competition competition) {
		String updateCompetition ="UPDATE competition SET competition_id= ?, name_of_competition= ?, start_date= ?," + 
		" end_date= ?, description= ?, minutes_to_finish= ?";
		jdbcTemplate.update(updateCompetition, competition.getCompetitionId(), competition.getNameOfCompetition(),
		competition.getStartDate(), competition.getEndDate(), competition.getDescription(), competition.getMinutesToFinish());	
	}
	
	private Competition mapRowToCompetition(SqlRowSet results) {
		Competition competition = new Competition();
		competition.setCompetitionId(results.getLong("competition_id"));
		competition.setNameOfCompetition(results.getString("name_of_competition"));
		competition.setStartDate(results.getDate("start_date").toLocalDate());
		competition.setEndDate(results.getDate("end_date").toLocalDate());
		competition.setDescription(results.getString("description"));
		competition.setMinutesToFinish(results.getInt("minutes_to_finish"));
		return competition;
	}
	private Person mapRowToPerson(SqlRowSet results) {
		Person person = new Person();
		person.setPeopleId(results.getLong("people_id"));
		person.setFamilyId(results.getInt("family_id"));
		person.setName(results.getString("name"));
		person.setParent(results.getBoolean("is_parent"));
		person.setInactive(results.getBoolean("inactive"));
		return person;
	}

	@Override
	public Competition showCompetitionDetails(long competitionId) {
		Competition comp = new Competition();
		String sql = "SELECT * FROM competition WHERE competition_id = ?; ";
		SqlRowSet results=jdbcTemplate.queryForRowSet(sql, competitionId);
		while (results.next()) {
			comp = mapRowToCompetition(results);
		}
		return comp;
	}
}
