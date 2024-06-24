package models;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "books")
public class BookList {
	
	@XmlElement(name = "book")
	private List<Book> bookList;

	// Default constructor, required for JAXB
	public BookList() {
	}

    // Constructor to initialize the BookList object with a given list of books
	public BookList(List<Book> bookList) {
		this.bookList = bookList;
	}	

    // Getter for the list of books
	public List<Book> getBooksList() {
		return bookList;
	}
	
    // Setter for the list of books
	public void setBooksList(List<Book> bookList) {
		this.bookList = bookList;
	}
}