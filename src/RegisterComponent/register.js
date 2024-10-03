import React, { useState } from 'react';
import { Button, Form, Message } from 'semantic-ui-react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './register.css'; // Import the CSS file

const Register = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const navigate = useNavigate();

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/api/auth/register', { name, email, password });
      setSuccessMessage('Registration successful. Please log in.');
      setTimeout(() => navigate('/'), 2000);  // Redirect to login page after 2 seconds
    } catch (error) {
      setErrorMessage('Registration failed. Try a different email.');
    }
  };

  return (
    <div className="register-container">
      <h2 className="register-title">Register</h2>
      <Form onSubmit={handleRegister} error={!!errorMessage} success={!!successMessage} className="register-form">
        <Form.Input
          label='Name'
          type='text'
          value={name}
          onChange={e => setName(e.target.value)}
          required
          className="register-input"
        />
        <Form.Input
          label='Email'
          type='email'
          value={email}
          onChange={e => setEmail(e.target.value)}
          required
          className="register-input"
        />
        <Form.Input
          label='Password'
          type='password'
          value={password}
          onChange={e => setPassword(e.target.value)}
          required
          className="register-input"
        />
        <Button type='submit' className="register-button">Register</Button>
        {errorMessage && <Message error content={errorMessage} className="error-message" />}
        {successMessage && <Message success content={successMessage} className="success-message" />}
      </Form>
    </div>
  );
};

export default Register;

