package com.techelevator.model.DAO.JDBC;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.Book;
import com.techelevator.model.DAO.BookDAO;

@Component
public class JDBCBookDAO implements BookDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCBookDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void addBookToPerson(Book book, long personId) {
		String sql = "INSERT INTO people_book (book_id, people_id) " +
					"VALUES (?, ?)";
		jdbcTemplate.update(sql, getBookIdByIsbn(book.getIsbn()), personId);
	}
	
	@Override
	public long getBookIdByIsbn(long isbn) {
		String sql = "SELECT book_id FROM book WHERE isbn = ?";
		SqlRowSet set = jdbcTemplate.queryForRowSet(sql, isbn);
		set.next();
		return set.getLong(1);
	}

	@Override
	public List<Book> getListOfBooksByPerson(long personId) {
		ArrayList<Book> books = new ArrayList<Book>();
		String sql = "SELECT * FROM book JOIN people_book ON people_book.book_id = book.book_id " +
				"WHERE people_book.people_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, personId);
		while(results.next()) {
			Book book = mapRowToBook(results);
			books.add(book);
		}
		return books;
	}

	@Override
	public List<Book> getListOfBooksByFamily(long familyId) {
		ArrayList<Book> books = new ArrayList<Book>();
		String sql = "SELECT DISTINCT book.* FROM book JOIN people_book ON people_book.book_id = book.book_id " + 
				"JOIN people ON people.people_id = people_book.people_id " + 
				"JOIN family ON people.family_id = family.family_id " + 
				"WHERE family.family_id = ? LIMIT 10;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, familyId);
		while(results.next()) {
			Book book = mapRowToBook(results);
			books.add(book);
		}
		return books;
	}
	
	@Override
	public void setBookInactive(long personId, long isbn) {
		String sql = "INSERT INTO people_book (inactive) VALUES (?) WHERE people_id = ? AND book_id = ?";
		long bookId = getBookIdByIsbn(isbn);
		jdbcTemplate.update(sql, personId, bookId);
	}

	@Override
	public void addNewBook(Book newBook) {
		String sql = "INSERT INTO book (title, author, isbn, image) " +
				"VALUES (?, ?, ?, ?) RETURNING book_id";
		SqlRowSet set = jdbcTemplate.queryForRowSet(sql, newBook.getTitle(), newBook.getAuthor(), newBook.getIsbn(), newBook.getImage());
		set.next();
	}
	
	
	private long getNextBookId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('book_book_id_seq')");
		if(nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new book");
		}
	}
	
	private long getNextPeopleBookId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('people_book_id_seq')");
		if(nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new book");
		}
	}
	

	
	private Book mapRowToBook(SqlRowSet results) {
		Book book = new Book();
		book.setBookId(results.getLong("book_id"));
		book.setTitle(results.getString("title"));
		book.setAuthor(results.getString("author"));
		book.setImage(results.getString("image"));
		book.setIsbn(results.getLong("isbn"));
		return book;
	}

	@Override
	public Book getBookDetail(long bookId) {
		Book book = new Book();
		String sql = "SELECT * FROM book WHERE book_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, bookId);
		while(results.next()) {
			book = mapRowToBook(results);
		}
		return book;
	}
	
	@Override
	public List<Book> getListOfBooksNotReading(long familyId, long personId) {
		List<Book> noBooks = new ArrayList<Book>(); 
		List<Book> yesBooks = new ArrayList<Book>();
		noBooks = getListOfBooksByPerson(personId);
		yesBooks = getListOfBooksByFamily(familyId);
		yesBooks.removeAll(noBooks);
		return yesBooks;
	}
	
	@Override
	public long getPeopleBookId(long peopleId, long isbn) {
		String sql = "SELECT people_book.people_book_id FROM people_book JOIN book ON book.book_id = people_book.book_id"
				+ " WHERE people_book.people_id = ? AND book.isbn = ?;";
		SqlRowSet set = jdbcTemplate.queryForRowSet(sql, peopleId, isbn);
		set.next();
		return set.getLong(1);
	}
}
