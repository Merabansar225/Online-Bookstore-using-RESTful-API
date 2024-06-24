$(document).ready(function () {

  // Function to fetch books and display in the table
  function fetchBooks() {
    $.ajax({
      url: 'http://localhost:8080/RESTful-API-Assignment/books*', 
      type: 'GET',
      crossDomain: true,
      cache: false,
      dataType: 'json',
      success: function (data) {
        let booksTable = '';
        data.forEach((book) => {
          booksTable += `
          <tr>
            <td>${book.id}</td>
            <td>${book.title}</td>
            <td>${book.author}</td>
            <td>${book.date}</td>
            <td>${book.genres}</td>
            <td>${book.characters}</td>
            <td>${book.synopsis}</td>
            <td>
              <button class="btn btn-warning edit-button" data-book-id="${book.id}">
                <i class="fas fa-edit"></i>
              </button>
              &nbsp;
              <button class="btn btn-danger delete-button" data-book-id="${book.id}">
                <i class="fas fa-trash"></i>
              </button>
            </td>
          </tr>`;
        });
        $('#books-tbody').html(booksTable);
      },
      error: function (error) {
        console.error('There was an Error while fetching books:', error);
      },
    });
  }

  // Add new book
  $('#addBookBtn').on('click', function () {

    let newBook = {
      title: $('#title').val(),
      author: $('#author').val(),
      date: $('#date').val(),
      genres: $('#genres').val(),
      characters: $('#characters').val(),
      synopsis: $('#synopsis').val(),
    };

    $.ajax({
      url: 'http://localhost:8080/RESTful-API-Assignment/books*', 
      type: 'POST',
      data: JSON.stringify(newBook),
      cache: false,
      contentType: 'application/json',
      success: function (data) {
        $('#addNewBookModal').modal('hide');
        location.reload(data);
      },
      error: function (error) {
        console.error('There was an Error while adding book:', error);
      },
    });
  });
  fetchBooks();


  // Pre fill Update form modal 
  $(document).on("click", ".edit-button", function () {
    let bookId = $(this).data('book-id');
    $.ajax({
      url: `http://localhost:8080/RESTful-API-Assignment/books*?id=${bookId}`, 
      type: 'GET',
      crossDomain: true,
      cache: false,
      dataType: 'json',
      success: function (data) {
		  let book = data;
        $('#update-id').val(book.id);
        $('#update-title').val(book.title);
        $('#update-author').val(book.author);
        $('#update-date').val(book.date);
        $('#update-genres').val(book.genres);
        $('#update-characters').val(book.characters);
        $('#update-synopsis').val(book.synopsis);
        $('#updateBookModal').data('book-id', bookId).modal('show');
      },
      error: function (error) {
        console.error('There was an Error while fetching book:', error);
      },
    });
  });

  // Update book modal
  $('#updateBookBtn').on('click', function () {

    let updatedBook = {
      id: $('#update-id').val(),
      title: $('#update-title').val(),
      author: $('#update-author').val(),
   	  date: $('#update-date').val(),
	  genres: $('#update-genres').val(),
	  characters: $('#update-characters').val(),
	  synopsis: $('#update-synopsis').val(),
	};


	$.ajax({
	  url: `http://localhost:8080/RESTful-API-Assignment/books*?id=${updatedBook.id}`, 
	  type: 'PUT',
	  cache: false,
	  crossDomain: true,
	  dataType: 'json',
	  data: JSON.stringify(updatedBook),
	  contentType: 'application/json',
	  success: function (data) {
	    $('#updateBookModal').modal('hide');
	    location.reload(data); 
	
	  },
	  error: function (error) {
	    console.error('There was an Error while updating the book:', error);
	  },
	 });
	});
	fetchBooks();



	// Select correct ID when delete button is clicked 
	$(document).on('click', '.delete-button', function () {
	  let bookId = $(this).data('book-id');
	  $('#deleteConfirmationModal').data('book-id', bookId).modal('show');
	});
	
	
	// Delete confirmation modal
	$('#deleteBookBtn').on('click', function () {
	  let bookId = $('#deleteConfirmationModal').data('book-id');
	
	  $.ajax({
	    url: `http://localhost:8080/RESTful-API-Assignment/books*?id=${bookId}`,
	    type: 'DELETE',
	    cache: false,
	    contentType: 'application/json',
	    data: JSON.stringify({ id: bookId }), 
	    success: function (data) {
	      $('#deleteConfirmationModal').modal('hide');
	      location.reload(data); 
	    },
	    error: function (error) {
	      console.error('There was an Error while deleting the book:', error);
	    },
	  });
	});
	fetchBooks();
});





// Create pagination using jQuery
$(document).ready(function() {
    $('#bookTable').DataTable({ 
    	"pagingType": "full_numbers",
    	"pageLength": 25
    });
});





// Code for getting data in 3 formats (json, xml, plain text)

document.addEventListener('DOMContentLoaded', (DOM) => {
    console.log("DOM is fully loaded.");

    // Function to create a new XMLHttpRequest object
    function getRequestObject() {
        if (window.XMLHttpRequest) {
            return (new XMLHttpRequest());
        } else if (window.ActiveXObject) {
            return (new ActiveXObject("Microsoft.XMLHTTP"));
        } else {
            return (null);
        }
    }

// An event listener is added for the format dropdown so different format data is fetched from the server when different formats are selected
document.getElementById("format-dropdown").addEventListener("change", function () {
    var selectedFormat = this.value;
    console.log("Selected format: ", selectedFormat);
    if (selectedFormat == 'application/json') {
        return fetchDataFromServer('application/json');
    }
    else if (selectedFormat == 'application/xml') {
        return fetchDataFromServer('application/xml');
    }
    else if (selectedFormat == 'text/plain') {
        return fetchDataFromServer('text/plain');
    }
    else {
        return fetchDataFromServer('application/json');
    }
});

// Function to make an AJAX request to the server based on the selected format
function fetchDataFromServer(format) {
    console.log("Fetch data for format: ", format);
    var request = getRequestObject();
    request.onreadystatechange = function () { showResponseAlert(request) };
    request.open("GET", "http://localhost:8080/RESTful-API-Assignment/books*", true);
    request.setRequestHeader('Accept', format);
    request.send(null);
}

// Function to handle the server response based on the format
function showResponseAlert(request) {
    if ((request.readyState == 4) && (request.status == 200)) {
        var selectedFormat = document.getElementById("format-dropdown").value;
        console.log("Response for format: ", selectedFormat);
        if ((this.readyState == 4) && (this.status == 200)) {
	            var response = this.responseText;
	            if (format.includes("application/json")) {
	                var jsonData = JSON.parse(response);
	                console.log(jsonData);
	            } else if (format.includes("application/xml")) {
	                var xmlData = new DOMParser().parseFromString(response, "application/xml");
	                console.log(xmlData);
	            } else if (format.includes("text/plain")) {
	                console.log(response);
	            } else {
	                console.log("Invalid data format");
	            }
	        } else {
	            console.log("Request failed");
	        }
	  	}
  	}

});

