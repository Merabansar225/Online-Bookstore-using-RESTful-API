<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="models.Book"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>InkSpirations</title>

<!-- Include Bootstrap and JQuery -->
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


<!-- Include Datatable CSS and Javascript -->
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.22/css/jquery.dataTables.min.css" />
<script type="text/javascript" src="https://cdn.datatables.net/1.10.22/js/jquery.dataTables.min.js"></script>

<!-- Include FontAwesome -->
<script src="https://kit.fontawesome.com/c44302f88f.js" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />

<!-- Include CSS and JavaScript -->
<link rel="stylesheet" href="./css/main.css" type="text/css" />
<script src="./scripts/script.js" type="text/javascript"></script> 

<!-- Google Font -->
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">

<style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap');
h1 {
	font-family: 'Poppins', sans-serif;
	font-size: 48px;
	font-weight: 700;
	color: white;
}

.subheading {
	font-family: 'Poppins', sans-serif;
	font-size: 24px;
	font-weight: 400;
	color: white;
}
</style>

</head>

<body>

	<!-- Header section with title, subtitle and dropdown button-->
	<div class="header">
		<h1>InkSpirations &nbsp;🎨📚</h1>
		<h2 class="subheading">Discover Your Next Escape...</h2>
		<div class="d-flex justify-content-between align-items-center">
			<div>
				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#addNewBookModal">
					<i class="fas fa-book-medical"></i> &nbsp; Add New Book
				</button>
				<div class="dropdown">
					<select id="format-dropdown" class="form-control">
						<option value="" disabled selected>Format</option>
						<option id="json" value="json">JSON</option>
						<option id="xml" value="xml">XML</option>
						<option id="plainText" value="text">Plain Text</option>
					</select>
				</div>
			</div>
		</div>
	</div>


	<!-- Table Container -->
	<div class="container mt-3">
		<div class="row">
			<div class="col">
				<table id="bookTable" class="display table table-striped table-bordered">
					<thead>
						<tr>
							<th>ID</th>
							<th>Title</th>
							<th>Author</th>
							<th>Published Date</th>
							<th>Genres</th>
							<th>Characters</th>
							<th>Synopsis</th>
							<th></th>
						</tr>
					</thead>
					<tbody id="books-tbody">
						<tr>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td>
							    <div class="d-inline-block">
							        <button class="btn btn-warning edit-button" data-book-id="1" data-toggle="tooltip" data-placement="top" title="Edit">
							            <i class="fas fa-edit"></i>
							        </button>
							    </div>
							    <div class="d-inline-block">
							        <button class="btn btn-danger delete-button" data-toggle="tooltip" data-placement="top" title="Delete">
							            <i class="fas fa-trash"></i>
							        </button>
							    </div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>






	<!-- Add New Book Modal -->
		<div class="modal fade" id="addNewBookModal" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">
						<i class="fa-sharp fa-solid fa-book-open"></i>&nbsp;Add New Book
					</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="addBookForm">
						<div class="modal-body">

							<label for="title">Title</label> 
							<input type="text" id="title" name="title" placeholder="Book Title" class="form-control"><br> 
							
							<label for="author">Author</label> 
							<input type="text" id="author" name="author" placeholder="Author name" class="form-control"> <br> 
							
							<label for="date">Date</label>
							<input type="date" id="date" name="date" placeholder="Release Date" class="form-control"> <br>

							<label for="genres">Genres</label> 
							<input type="text" id="genres" name="genres" placeholder="Genres" class="form-control"><br> 
							
							<label for="characters">Characters</label> 
							<input type="text" id="characters" name="characters" placeholder="Character names" class="form-control"> <br>

							<label for="synopsis">Synopsis</label> 
							<input type="text" id="synopsis" name="synopsis" placeholder="Synopsis" class="form-control"> <br>

						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal"><i class="fa-sharp fa-regular fa-circle-xmark"></i>&nbsp;Close</button>
							<button type="button" id="addBookBtn" class="btn btn-primary">Add New Book</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>



	<!-- Update Book Modal -->
	<div class="modal fade" id="updateBookModal" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Update Book</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="updateBookForm">
						<div class="form-group">
							<label for="update-id">ID</label> <input type="text"
								class="form-control" id="update-id" name="id" readonly>
						</div>
						<div class="form-group">
							<label for="update-title">Title</label> <input type="text"
								class="form-control" id="update-title" name="title">
						</div>
						<div class="form-group">
							<label for="update-author">Author</label> <input type="text"
								class="form-control" id="update-author" name="author">
						</div>
						<div class="form-group">
							<label for="update-date">Date</label> <input type="date"
								class="form-control" id="update-date" name="date">
						</div>
						<div class="form-group">
							<label for="update-genres">Genres</label> <input type="text"
								class="form-control" id="update-genres" name="genres">
						</div>
						<div class="form-group">
							<label for="update-characters">Characters</label> <input
								type="text" class="form-control" id="update-characters"
								name="characters">
						</div>
						<div class="form-group">
							<label for="update-synopsis">Synopsis</label> <input type="text"
								class="form-control" id="update-synopsis" name="synopsis">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" id="updateBookBtn" class="btn btn-primary">Update
						Book</button>
				</div>
			</div>
		</div>
	</div>



	<!-- Delete Confirmation Modal -->
	<div class="modal fade" id="deleteConfirmationModal" tabindex="-1"
		role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="deleteModalLabel">Delete Book</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">Are you sure you want to delete this book?</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
					<button type="button" id="deleteBookBtn" class="btn btn-danger">Delete</button>
				</div>
			</div>
		</div>
	</div>



</body>
</html>
