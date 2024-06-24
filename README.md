This README file contains the instructions for the RESTful API project.
# RESTFUL API PROJECT - INKSPIRATION

This is my RESTful API application project for the Cloud and Enterprise Module which allows you to manage a bookstore's inventory by providing a RESTful API to Create, Read, Update, and Delete (CRUD) books in the database. The API supports JSON, XML and Plain text formats.

## PACKAGES AND CLASSES OVERVIEW
- controllers: This package contains the controller class which handles incoming HTTP requests:
	- BooksControllerAPI.java: An API controller that provides CRUD functionality for JSON, XML and plain text formats.

- database: This package includes classes related to database connectivity and CRUD operations:
	BookDAO: The class that communicates with the database, providing all CRUD operations.

- models: This package contains model classes which define the structure of objects:
	- Book.java: Represents the structure of a book object.
	- BookList.java: Represents a collection of book objects.

- web app: This directory contains the view component (JSP files), CSS stylesheets and scripts:
	- index.jsp: The main page of the website that includes modals for inserting, updating and deleting books.
	- css directory: Contains the main.css stylesheet corresponding to the index.jsp page.
	- scripts directory: Contains the script.js file, which implements CRUD operations using jQuery and AJAX, provides pagination using DataTables and functionality to fetch data in all 3 formats (JSON, XML and plain text).

## RUNNING THE APPLICATION
To run the application, simply navigate to the web app folder and run the index.jsp file.

## NOTES ON STYLES AND SCRIPTS
- The styling for the index.jsp page is contained within the main.css file in the css directory.

- The JavaScript functionality, including CRUD operations, pagination and data fetching, is implemented in the script.js file in the scripts directory.

