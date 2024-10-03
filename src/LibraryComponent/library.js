import React, { useEffect, useState } from 'react';
import { Card, Button, Icon, Modal, Form, Message } from 'semantic-ui-react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './library.css'; // Import the CSS file

const LibraryDashboard = () => {
  const navigate = useNavigate();
  const [books, setBooks] = useState([]);
  const [openModal, setOpenModal] = useState(false);
  const [newBook, setNewBook] = useState({ title: '', author: '', year: '', isbn: '' });
  const [errorMessage, setErrorMessage] = useState('');
  const [editMode, setEditMode] = useState(false);

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/user/books');
      setBooks(response.data);
      setErrorMessage(''); // Clear any previous error messages
    } catch (error) {
      console.error('Error fetching books:', error);
      setErrorMessage('Failed to fetch books. Please try again later.');
    }
  };

  const handleDeleteBook = async (isbn) => {
    try {
      await axios.delete(`http://localhost:8080/api/user/deleteBook/${isbn}`);
      setBooks(books.filter(book => book.isbn !== isbn));
      alert('Book deleted successfully');
    } catch (error) {
      console.error('Error deleting book:', error);
      setErrorMessage('Failed to delete the book. Please try again.');
    }
  };

  const handleAddBook = async () => {
    const { title, author, year, isbn } = newBook;
    if (!title || !author || !year || !isbn) {
      setErrorMessage('All fields are required.');
      return;
    }

    try {
      if (editMode) {
        await axios.put(`http://localhost:8080/api/user/updateBook/${isbn}`, {
          title,
          author,
          year,
        });
        alert('Book updated successfully');
      } else {
        await axios.post('http://localhost:8080/api/user/addBook', newBook);
        alert('Book added successfully');
      }
      fetchBooks(); // Refresh the book list after adding or updating a book
      setOpenModal(false); // Close the modal
      setNewBook({ title: '', author: '', year: '', isbn: '' }); // Reset form fields
      setEditMode(false); // Reset edit mode
    } catch (error) {
      console.error(error);
      setErrorMessage(error.response?.data || 'Error adding or updating book');
    }
  };

  const handleEditBook = (book) => {
    setNewBook(book);
    setEditMode(true);
    setOpenModal(true);
  };

  const handleLogout = () => {
    // Perform logout logic, e.g., clearing tokens or user data
    // Redirect to home
    navigate('/');
  };

  return (
    <div className="library-dashboard">
      <h1>Library Dashboard</h1>

      {/* Logout Button */}
      <Button color='red' onClick={handleLogout}>
        Logout
      </Button>

      {/* Add/Edit Book Button */}
      <Button color='green' onClick={() => { setOpenModal(true); setEditMode(false); setNewBook({ title: '', author: '', year: '', isbn: '' }); }}>
        <Icon name='book' /> Add New Book
      </Button>

      {/* Book Cards */}
      <Card.Group>
        {books.length > 0 ? (
          books.map(book => (
            <Card key={book.isbn}>
              <Card.Content>
                <Card.Header>{book.title}</Card.Header>
                <Card.Meta>{book.author}</Card.Meta>
                <Card.Description>Published: {book.year}</Card.Description>
              </Card.Content>
              <Card.Content extra>
                <Button color='blue' onClick={() => handleEditBook(book)}>
                  <Icon name='edit' /> Edit
                </Button>
                <Button color='red' onClick={() => handleDeleteBook(book.isbn)}>
                  <Icon name='trash' /> Delete
                </Button>
              </Card.Content>
            </Card>
          ))
        ) : (
          <p>No books found.</p>
        )}
      </Card.Group>

      {/* Modal for Adding/Editing a Book */}
      <Modal open={openModal} onClose={() => setOpenModal(false)}>
        <Modal.Header>{editMode ? 'Edit Book' : 'Add a New Book'}</Modal.Header>
        <Modal.Content>
          <Form>
            <Form.Input
              label='Title'
              value={newBook.title}
              onChange={e => setNewBook({ ...newBook, title: e.target.value })}
              required
            />
            <Form.Input
              label='Author'
              value={newBook.author}
              onChange={e => setNewBook({ ...newBook, author: e.target.value })}
              required
            />
            <Form.Input
              label='Year'
              type='number'
              value={newBook.year}
              onChange={e => setNewBook({ ...newBook, year: e.target.value })}
              required
            />
            <Form.Input
              label='ISBN'
              type='text'
              value={newBook.isbn}
              readOnly={editMode} // Make ISBN read-only in edit mode
              onChange={e => setNewBook({ ...newBook, isbn: e.target.value })}
              required
            />
          </Form>
          {errorMessage && <Message error content={errorMessage} />}
        </Modal.Content>
        <Modal.Actions>
          <Button color='black' onClick={() => { setOpenModal(false); setNewBook({ title: '', author: '', year: '', isbn: '' }); }}>
            Cancel
          </Button>
          <Button color='green' onClick={handleAddBook}>
            {editMode ? 'Update Book' : 'Add Book'}
          </Button>
        </Modal.Actions>
      </Modal>
    </div>
  );
};

export default LibraryDashboard;





