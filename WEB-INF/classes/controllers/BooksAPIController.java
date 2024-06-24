package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


import database.BookDAO;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import models.Book;
import models.BookList;


//A servlet handling CRUD operations for books 
@WebServlet("/books")
public class BooksAPIController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// Method handling GET requests. Fetches all books from the database and sends back in the format specified in the Accept header
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Add CORS headers
	    response.addHeader("Access-Control-Allow-Origin", "*");
	    response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
	    response.addHeader("Access-Control-Allow-Credentials", "true");
	    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	    
	    // Creates a DAO and retrieves all books from the database
		PrintWriter out = response.getWriter(); 
		BookDAO dao = new BookDAO();
		ArrayList<Book> allBooks = dao.getAllBooks(); 

		// The format is checked and appropriate response type is set 
		String format = request.getHeader("Accept");
		String data = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

	    if (format == null || format.contains("application/json") || format.contains("*/*")) {
	    	response.setContentType("application/json");
	    	Gson gson = new Gson();
			String json = gson.toJson(allBooks);
			out.write(json);
			out.close();
		}
		else if (format.equals("application/xml")) {			
			try {
				BookList bkl = new BookList(allBooks);
				StringWriter sw = new StringWriter();
				JAXBContext context = JAXBContext.newInstance(BookList.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.marshal(bkl, sw);
				System.out.println(sw.toString());
				response.setContentType("application/xml");
				out.write(sw.toString());
			} catch (JAXBException e) {
				System.out.println("Error processing XML: " + e.getMessage());
				e.printStackTrace();
			}
		}
		else if (format.equals("text/plain")) {
			response.setContentType("text/plain");
			for (int i = 0; i < allBooks.size(); i++) {
				Book book = allBooks.get(i);
				out.write(book.toString() + "\n");
			}
		}
		else {
			System.out.println("Incorrect data format GET.");
		}
	}
	
	
	// Method handling POST requests. Creates a book from the request body and adds it to the database
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Add CORS headers
	    response.addHeader("Access-Control-Allow-Origin", "*");
	    response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
	    response.addHeader("Access-Control-Allow-Credentials", "true");
	    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	    
	    BookDAO dao = new BookDAO(); 
		PrintWriter out = response.getWriter();
		
		// The format is checked and appropriate response type is set 
		String format = request.getHeader("Content-Type");
		String data = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

		if(format.equals("application/json")) {
			Gson gson = new Gson();
			Book bk = gson.fromJson(data, Book.class);
			System.out.println(bk.getTitle());
			System.out.println(bk.getAuthor());
			System.out.println(bk.getDate());
			System.out.println(bk.getGenres());
			System.out.println(bk.getCharacters());
			System.out.println(bk.getSynopsis());

			try {
				dao.insertBook(bk); 
				out.write("Book inserted");
			} catch (SQLException e) { 
				e.printStackTrace();
			}
			out.close();
		}

		else if(format.equals("application/xml")) {
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(Book.class); 
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller(); 
				Book bk = (Book) jaxbUnmarshaller.unmarshal(new StringReader(data));

				try {
					dao.insertBook(bk); 
					out.write("Book inserted");
				} catch (SQLException e) { 
					e.printStackTrace();
				}
				out.close();

			} catch (JAXBException e) {
				System.out.println("Error processing XML: " + e.getMessage());
				e.printStackTrace();
			}
		}

		else if (format.equals("text/plain")) {
			String[] bookTextFormat = data.split("#");
			for(String s : bookTextFormat) {
				System.out.println(s);
			}
			if (bookTextFormat.length >= 6) {
				Book bk = new Book(bookTextFormat[0], bookTextFormat[1], bookTextFormat[2], bookTextFormat[3], bookTextFormat[4], bookTextFormat[5]);
				try {
					dao.insertBook(bk); 
					out.write("Book inserted");
				} catch (SQLException e) { 
					e.printStackTrace();
				}
				out.close();
			} else {
				System.out.println("Incorrect data format POST.");
			}
		}
	}
	
	
	// Method handling PUT requests. Updates a book in the database with the data from the request body
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Add CORS headers
	    response.addHeader("Access-Control-Allow-Origin", "*");
	    response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
	    response.addHeader("Access-Control-Allow-Credentials", "true");
	    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	    
	    BookDAO dao = new BookDAO(); 
		PrintWriter out = response.getWriter();

		// The format is checked and appropriate response type is set 
		String format = request.getHeader("Content-Type");
		String data = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

		System.out.println(data);

		if(format.equals("application/json")) {
			Gson gson = new Gson();
			Book bk = gson.fromJson(data, Book.class);
			System.out.println(bk.getTitle());
			System.out.println(bk.getAuthor());
			System.out.println(bk.getDate());
			System.out.println(bk.getGenres());
			System.out.println(bk.getCharacters());
			System.out.println(bk.getSynopsis());

			try{
				dao.updateBook(bk); 
				out.write("Book updated");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			out.close();
		}
		
		else if(format.equals("application/xml")) {
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(Book.class); 
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller(); 
				Book bk = (Book) jaxbUnmarshaller.unmarshal(new StringReader(data));

				try {
					dao.updateBook(bk); 
					out.write("Book updated");
				} catch (SQLException e) { 
					e.printStackTrace();
				}
				out.close();

			} catch (JAXBException e) {
				System.out.println("Error processing XML: " + e.getMessage());
				e.printStackTrace();
			}
		}
		else if (format.equals("text/plain")) {
			String[] bookTextFormat = data.split("#");
			for(String s : bookTextFormat) {
				System.out.println(s);
			}
			if (bookTextFormat.length >= 7) {
				Book bk = new Book(Integer.parseInt(bookTextFormat[0]), bookTextFormat[1], bookTextFormat[2], bookTextFormat[3], bookTextFormat[4], bookTextFormat[5], bookTextFormat[6]);
				try {
					dao.updateBook(bk); 
					out.write("Book updated");
				} catch (SQLException e) { 
					e.printStackTrace();
				}
				out.close();
			} else {
				System.out.println("Incorrect data format PUT.");
			}
		}
	}
	
	// Method handling DELETE requests. Deletes a book from the database identified by the data in the request body
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Add CORS headers
	    response.addHeader("Access-Control-Allow-Origin", "*");
	    response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
	    response.addHeader("Access-Control-Allow-Credentials", "true");
	    response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	    
	    BookDAO dao = new BookDAO();
		PrintWriter out = response.getWriter();
		
		// The format is checked and appropriate response type is set 
		String format = request.getHeader("Content-Type");
		String data = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
		System.out.println("Book Deleted: "+data);
		if(format.equals("application/json")) {
			Gson gson = new Gson();
			Book book = gson.fromJson(data, Book.class);
			try{
				dao.deleteBook(book); 
				out.write("Book deleted");
			} catch (SQLException e) { 
				e.printStackTrace();
			}
			out.close();
		}
	    else if (format.equals("application/xml")) {
	    	try {
				JAXBContext jaxbContext = JAXBContext.newInstance(Book.class); 
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller(); 
				Book book = (Book) jaxbUnmarshaller.unmarshal(new StringReader(data));

				try {
					dao.deleteBook(book); 
					out.write("Book deleted");
				} catch (SQLException e) { 
					e.printStackTrace();
				}
				out.close();

			} catch (JAXBException e) {
				System.out.println("Error processing XML: " + e.getMessage());
				e.printStackTrace();
			}

	    }
	    else if(format.equals("text/plain")) {
			int Id;
			try {
				Id = Integer.parseInt(data.trim());
				Book book = new Book();
				book.setId(Id);
				try {
					
					Book bk = new Book();
					bk.setId(Id);
					dao.deleteBook(bk);
					out.write("Book deleted");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				out.close();
			} catch (NumberFormatException e) {
				System.out.println("Invalid plain text input: " + e.getMessage());
				e.printStackTrace();
			}
		}
	    else {
			System.out.println("Incorrect data format DELETE.");
	    }
	}
	
}
