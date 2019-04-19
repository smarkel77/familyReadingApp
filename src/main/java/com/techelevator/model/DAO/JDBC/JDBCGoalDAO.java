package com.techelevator.model.DAO.JDBC;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Goal;
import com.techelevator.model.DAO.GoalDAO;

@Component
public class JDBCGoalDAO implements GoalDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCGoalDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void createNewGoal(Goal newGoal, long personId) {
		String sql = "INSERT INTO goal (goal_id, name_of_goal, start_date, number_of_days, " +
				"description, minutes_to_reach_goal, is_complete) VALUES " +
				"(?, ?, ?, ?, ?, ?, ?)";
		newGoal.setGoalId(getNextGoalId());
		jdbcTemplate.update(sql, newGoal.getGoalId(), newGoal.getNameOfGoal(), newGoal.getStartDate(), 
				newGoal.getNumberOfDays(), newGoal.getDescription(), newGoal.getMinutesToReachGoal(), 
				newGoal.isComplete());
		String sqlJoin = "INSERT INTO goal_people (goal_id, people_id) VALUES (?,?);";
		jdbcTemplate.update(sqlJoin, newGoal.getGoalId(), personId);
	}

	@Override
	public void addGoalToPerson(Goal newGoal, long personId) {
		String sql = "INSERT INTO goal_people (goal_id, people_id) " +
					"VALUES (?, ?)";
		jdbcTemplate.update(sql, newGoal.getGoalId(), personId);
	}

	@Override
	public List<Goal> getListOfAllGoals(long personId) {
		ArrayList<Goal> goals = new ArrayList<Goal>();
		String sql = "SELECT * FROM goal JOIN goal_people ON goal.goal_id = goal_people.goal_id WHERE " + 
				"goal_people.people_id = ? and goal.is_complete = false";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, personId);
		while(results.next()) {
			Goal goal = mapRowToGoal(results);
			int sumMinutes = getTotalMinutesReadByPersonAndGoal(goal.getGoalId(), personId);
			System.out.println(sumMinutes);
			goal.setMinutesRead(sumMinutes);
			goals.add(goal);
		}
		return goals;
	}

	@Override
	public List<Goal> getListOfFinishedGoals(long personId) {
		ArrayList<Goal> goals = new ArrayList<Goal>();
		String sql = "SELECT * FROM goal JOIN goal_people ON goal.goal_id = goal_people.goal_id WHERE " + 
				"goal_people.people_id = ? and goal.is_complete = true";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, personId);
		while(results.next()) {
			Goal goal = mapRowToGoal(results);
			goals.add(goal);
		}
		return goals;
	}

	@Override
	public List<Goal> getListOfOpenGoals(long personId) {
		ArrayList<Goal> goals = new ArrayList<Goal>();
		String sql = "SELECT * FROM goal JOIN goal_people ON goal.goal_id = goal_people.goal_id WHERE " + 
				"goal_people.people_id = ? and goal.is_complete = false";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, personId);
		while(results.next()) {
			Goal goal = mapRowToGoal(results);
			goals.add(goal);
		}
		return goals;
	}

	@Override
	public void updateGoal(Goal goal) {
		String sql = "UPDATE goal SET name_of_goal = ?, start_date = ?, number_of_days = ?, " +
					"description = ?, minutes_to_reach_goal = ?, is_complete = ?";
		jdbcTemplate.update(sql, goal.getNameOfGoal(), goal.getStartDate(), goal.getNumberOfDays(), 
				goal.getDescription(), goal.getMinutesToReachGoal(), goal.isComplete());
	}
	
	
	
	private long getNextGoalId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('goal_goal_id_seq')");
		if(nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new goal");
		}
	}
	
	private Goal mapRowToGoal(SqlRowSet results) {
		Goal goal = new Goal();
		goal.setGoalId(results.getLong("goal_id"));
		goal.setNameOfGoal(results.getString("name_of_goal"));
		goal.setStartDate(results.getDate("start_date").toLocalDate());
		goal.setNumberOfDays(results.getInt("number_of_days"));
		goal.setDescription(results.getString("description"));
		goal.setMinutesToReachGoal(results.getInt("minutes_to_reach_goal"));
		goal.setMinutesRead(results.getInt("minutes_read"));
		goal.setComplete(results.getBoolean("is_complete"));
		goal.setPersonId(results.getLong("people_id"));
		return goal;
	}

	@Override
	public List<Goal> getListOfGoalsByFamily(long familyId) {
		List<Goal> goals = new ArrayList<Goal>();
		String sql = "SELECT * FROM goal JOIN goal_people ON goal.goal_id = goal_people.goal_id " + 
				"JOIN people ON people.people_id = goal_people.people_id JOIN family ON " + 
				"people.family_id = family.family_id WHERE family.family_id = ? AND goal.is_complete = false";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, familyId);
		while(result.next()) {
			Goal goal = mapRowToGoal(result);
			int sumMinutes = getTotalMinutesReadByPersonAndGoal(goal.getGoalId(), goal.getPersonId());
			goal.setMinutesRead(sumMinutes);
			goals.add(goal);
		}
		return goals;
	}

	@Override
	public Goal getGoalDetail(long goalId) {
		Goal goal = new Goal();
		String sql = "SELECT * FROM goal WHERE goal_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, goalId);
		while(results.next()) {
			goal = mapRowToGoal(results);
		}
		return goal;
	}

	@Override
	public int getTotalMinutesReadByPersonAndGoal(long peopleId, long goalId) {
		int sum = 0;
		String str = "SELECT SUM(session.minutes_read), people.people_id FROM session JOIN people_book ON people_book.people_book_id = session.people_book_id " + 
				"JOIN people ON people.people_id = people_book.people_id JOIN goal_people ON goal_people.people_id = goal_people.people_id " + 
				"JOIN goal on goal.goal_id = goal_people.goal_id WHERE session.date_of_reading >= goal.start_date AND session.date_of_reading <= " + 
				"goal.start_date + goal.number_of_days AND people.people_id = ? AND goal.goal_id = ? GROUP BY people.people_id;";
		SqlRowSet result = jdbcTemplate.queryForRowSet(str, peopleId, goalId);
		while(result.next()) {
			sum = result.getInt("sum");
		}
		return sum;
	}

}
