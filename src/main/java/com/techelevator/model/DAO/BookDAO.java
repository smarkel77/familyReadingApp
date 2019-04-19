package com.techelevator.model.DAO;

import java.util.List;

import com.techelevator.model.Book;

public interface BookDAO {
	
	public void addNewBook(Book newBook);
	
	public void addBookToPerson(Book book, long personId);
	
	public List<Book> getListOfBooksByPerson(long personId);
	
	public List<Book> getListOfBooksByFamily(long familyId);
	
	public Book getBookDetail(long bookId);

	public List<Book> getListOfBooksNotReading(long familyId, long personId);

	long getPeopleBookId(long peopleId, long isbn);

	long getBookIdByIsbn(long isbn);

	void setBookInactive(long personId, long isbn);
}
