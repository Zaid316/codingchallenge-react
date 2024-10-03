import React, { useState } from 'react';
import { Button, Form, Message } from 'semantic-ui-react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './login.css'; // Import the CSS file

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');  
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/api/auth/login', { email, password });
      localStorage.setItem('token', response.data.jwt);  // Save JWT token
      localStorage.setItem('userRole', response.data.userRole); // Save user role if needed
      alert('Login Successful');
      navigate('/library'); 
    } catch (error) {
      setErrorMessage('Invalid email or password');
    }
  };

  return (
    <div className="login-container">
      <h2 className="login-title">Login</h2>
      <Form onSubmit={handleLogin} error={!!errorMessage} className="login-form">
        <Form.Input
          label='Email'
          type='email'
          value={email}
          onChange={e => setEmail(e.target.value)}
          required
          className="login-input"
        />
        <Form.Input
          label='Password'
          type='password'
          value={password}
          onChange={e => setPassword(e.target.value)}
          required
          className="login-input"
        />
        <Button type='submit' className="login-button">Login</Button>
        {errorMessage && <Message error content={errorMessage} className="error-message" />}
      </Form>
    </div>
  );
};

export default Login;

