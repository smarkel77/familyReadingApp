package com.techelevator.model.DAO.JDBC;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Person;
import com.techelevator.model.DAO.PersonDAO;

@Component
public class JDBCPersonDAO implements PersonDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCPersonDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void addPerson(long familyId, String name, boolean isParent, boolean isInactive) {
		String sqlInsertPerson = "INSERT INTO people(family_id, name, is_parent, inactive) " +
				   "VALUES(?, ?, ?, ?)";
		jdbcTemplate.update(sqlInsertPerson, familyId, name, isParent, false);
	}

	@Override
	public void deletePerson(long personId) {
		String sqlDelete = "UPDATE people SET inactive = true WHERE people_id = ?;";
		System.out.println(personId);
		jdbcTemplate.update(sqlDelete, personId);
		
	}

	@Override
	public List<Person> getListOfPeopleInFamily(long familyId) {
	ArrayList<Person> persons = new ArrayList<Person>();
	String sql = " SELECT * FROM people JOIN family ON people.family_id = family.family_id " +
	" WHERE people.family_id =? AND people.inactive = false; ";
	SqlRowSet results = jdbcTemplate.queryForRowSet(sql, familyId);
	while (results.next()) {
		Person person = mapRowToPerson(results);
		persons.add(person);
	}
		return persons;
	}
	
	private long getNextPersonId() {
		SqlRowSet nextPersonResult = jdbcTemplate.queryForRowSet("SELECT nextval('people_people_id_seq')");
		if(nextPersonResult.next()) {
			return nextPersonResult.getLong(1);
		}else {
			throw new RuntimeException("Something went wrong while getting new person id.");
		}
		
	}
	
	private Person mapRowToPerson(SqlRowSet results) {
		Person thePerson;
		thePerson = new Person();
		thePerson.setPeopleId(results.getLong("people_id"));
		thePerson.setFamilyId(results.getLong("family_id"));
		thePerson.setName(results.getString("name"));
		thePerson.setInactive(results.getBoolean("inactive"));
		thePerson.setParent(results.getBoolean("is_parent"));
		return thePerson;
	}

}
