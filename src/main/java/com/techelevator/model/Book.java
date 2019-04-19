package com.techelevator.model;

public class Book {
	
	private long bookId;
	private String title;
	private String author;
	private long isbn;
	private String image;
	
	@Override
	public boolean equals(Object anObject) {
		if(!(anObject instanceof Book)) {
			return false;
		}
		Book otherBook = (Book)anObject;
		return otherBook.getBookId() == (getBookId());
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public long getBookId() {
		return bookId;
	}
	public void setBookId(long bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public long getIsbn() {
		return isbn;
	}
	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}
	

}
