package models;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "book")
public class Book {
	private int id;
	private String title;
	private String author;
	private String date;
	private String genres;
	private String characters;
	private String synopsis;


	// Constructor for initializing the book object with provided values
	public Book(int id, String title, String author, String date, String genres, String characters, String synopsis) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.date = date;
		this.genres = genres;
		this.characters = characters;
		this.synopsis = synopsis;

	}

	// Constructor for initializing the book object with provided values  without an ID
	public Book(String title, String author, String date, String genres, String characters, String synopsis) {
		this.title = title;
		this.author = author;
		this.date = date;
		this.genres = genres;
		this.characters = characters;
		this.synopsis = synopsis;
	}


	// Constructor
	public Book() {
		
	}

	// Getters and setters for the book properties
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public String getCharacters() {
		return characters;
	}

	public void setCharacters(String characters) {
		this.characters = characters;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	
    // Method to return a string representation of the Book object, which can be useful for debugging purposes
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", date=" + date + ", genres=" + genres
				+ ", characters=" + characters + ", synopsis=" + synopsis + "]";
	}
}
