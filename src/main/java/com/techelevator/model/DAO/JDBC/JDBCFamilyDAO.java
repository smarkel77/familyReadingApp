package com.techelevator.model.DAO.JDBC;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Signup;
import com.techelevator.model.DAO.FamilyDAO;

@Component
public class JDBCFamilyDAO implements FamilyDAO{
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JDBCFamilyDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
		
		


	@Override
	public long addFamily(Signup signup) {
		String sql = "INSERT INTO family (family_name) VALUES (?) RETURNING family_id";
		SqlRowSet id = jdbcTemplate.queryForRowSet(sql, signup.getFamilyName());
		id.next();
		long familyId = id.getLong(1);
		System.out.println(familyId);
		return familyId;	
	}
	

}
