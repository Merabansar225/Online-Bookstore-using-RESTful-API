package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Book;


public class BookDAO {

	Book oneBook = null;
	Connection conn = null;
	Statement stmt = null;
	String user = "ansarmer";
	String password = "Vrigwale8";
	String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/" + user;
	
	// Connection to the cloud AWS database 
/*	Book oneBook = null;
	Connection conn = null;
	Statement stmt = null;
	String user = "admin";
	String password = "password";
	String url = "jdbc:mysql://cloudenterprise.cs6ewwqkutqk.eu-west-2.rds.amazonaws.com:3310/cloud";
*/	

	public BookDAO() {
		openConnection();
	}


    // Open database connection
	private void openConnection(){
		try{
		    Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		} catch(Exception e) { System.out.println(e); }

		try{
 			conn = DriverManager.getConnection(url, user, password);
		    stmt = conn.createStatement();
		} catch(SQLException se) { System.out.println(se); }	   
    }
	

    // Close database connection
	private void closeConnection(){
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private Book getNextBook(ResultSet rs){
		Book thisBook=null;
		try {

			thisBook = new Book(
					rs.getInt("id"),
					rs.getString("title"),
					rs.getString("author"),
					rs.getString("date"),
					rs.getString("genres"),
					rs.getString("characters"),
					rs.getString("synopsis"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return thisBook;		
	}

    // Retrieve all books from the database
	public ArrayList<Book> getAllBooks(){
		   
		ArrayList<Book> allBooks = new ArrayList<Book>();
		openConnection();
		
		try{
		    String selectSQL = "select * from books";
		    ResultSet rs1 = stmt.executeQuery(selectSQL);
		    while(rs1.next()){
		    	oneBook = getNextBook(rs1);
		    	allBooks.add(oneBook);
		   }

		    stmt.close();
		    closeConnection();
		} catch(SQLException se) { System.out.println(se); }

	   return allBooks;
   }



	// Retrieve a book by its ID - changed original code to prepared statement code 	
	public Book getBookByID(int id) {
	    
		openConnection();
	    oneBook = null;
	    try {
	        String selectSQL = "SELECT * FROM books WHERE id = ?";
	        PreparedStatement pstmt = conn.prepareStatement(selectSQL);
	        pstmt.setInt(1, id);

	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            oneBook = getNextBook(rs);
	        }

	        pstmt.close();
	        closeConnection();
	    } catch (SQLException se) {
	        System.out.println(se);
	    }

	    return oneBook;
	}

	
    // Search books by title or ID	
	public List<Book> searchBook(String searchBook) {
	    
		openConnection();
		oneBook = null;
	    List<Book> books = new ArrayList<>();
	    try {
	        int id = 0;
	        try {
	            id = Integer.parseInt(searchBook);
	        } catch (NumberFormatException e) {	
	        	System.out.println(e);
	        }
	        
	        String selectSQL = "SELECT * FROM books WHERE id = ? OR LOWER(title) LIKE ?";
	        PreparedStatement pstmt = conn.prepareStatement(selectSQL);
	        pstmt.setInt(1, id);
	        pstmt.setString(2, "%" + searchBook.toLowerCase() + "%");
	        
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            oneBook = getNextBook(rs);
	            books.add(oneBook);
	        }

	        pstmt.close();
	        closeConnection();
	    } catch (SQLException se) {
	        System.out.println(se);
	    }

	    return books;
	}


	// Insert a new book into the database	
	public boolean insertBook(Book b) throws SQLException {
		boolean bool = false;
		openConnection();
		try {
			String insertSQL = "INSERT INTO books (title, author, date, genres, characters, synopsis) VALUES (?, ?, ?, ?, ?, ?)";

			PreparedStatement pstmt = conn.prepareStatement(insertSQL);
			pstmt.setString(1, b.getTitle());
			pstmt.setString(2, b.getAuthor());
			pstmt.setString(3, b.getDate());
			pstmt.setString(4, b.getGenres());
			pstmt.setString(5, b.getCharacters());
			pstmt.setString(6, b.getSynopsis());
			
			int rowAdded = pstmt.executeUpdate();
			if (rowAdded > 0) {
				bool = true;
			}
			
			pstmt.close();
			closeConnection();
		} catch (SQLException s) {
			throw new SQLException("Book Not Added");
		}
		return bool;
	}



	// Update an existing book in the database
	public boolean updateBook(Book b) throws SQLException {
		boolean bool = false;
		openConnection();
		try {
	        String updateSQL = "UPDATE books SET title = ?, author = ?, date = ?, genres = ?, characters = ?, synopsis = ? WHERE id = ?";

			PreparedStatement pstmt = conn.prepareStatement(updateSQL);
			pstmt.setString(1, b.getTitle());
			pstmt.setString(2, b.getAuthor());
			pstmt.setString(3, b.getDate());
			pstmt.setString(4, b.getGenres());
			pstmt.setString(5, b.getCharacters());
			pstmt.setString(6, b.getSynopsis());
			pstmt.setInt(7, b.getId());
			
			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				bool = true;
			}
			
			pstmt.close();
			closeConnection();
		} catch (SQLException s) {
			throw new SQLException("Book Not Updated");
		}
		return bool;
	}


	// Delete a book from the database
	public boolean deleteBook(Book b) throws SQLException {
		boolean bool = false;
		openConnection();
		try {
		    String deleteSQL = "DELETE FROM books WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(deleteSQL);
			pstmt.setInt(1, b.getId());
			
			int rowsDeleted = pstmt.executeUpdate();
			if (rowsDeleted > 0) {
				bool = true;
			}
			
			pstmt.close();
			closeConnection();
		} catch (SQLException s) {
			throw new SQLException("Book Not Deleted");
		}		
		return bool;
	}


}
