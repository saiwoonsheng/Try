import React, { useState } from 'react';
import { login } from '../api/auth';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  //old (1)
  // const handleLogin = async (e) => {
  //   e.preventDefault();
  //   // TODO: Call login API
  //   alert("Login submitted: " + email);
  // };

  //Method (1)
// const handleLogin = async (e) => {
//   e.preventDefault();

//   try {
//     await login(email, password);
//     alert("Login successful: " + email);
//   } catch (error) {
//     if (error.response?.status === 401) {
//       const msg = error.response.data.error || "Invalid email or password.";
//       alert(msg);
//     } else if (error.response) {
//       // Handle other HTTP errors
//       alert("Login failed: " + error.response.data);
//     } else {
//       // Network or other unexpected error
//       alert("Login failed: " + error.message);
//     }
//   }
// };

//Method(2)
//   const handleLogin = async (e) => {
//   e.preventDefault();

//   try {
//     const response = await fetch('http://localhost:8080/auth/login', {
//       method: 'POST',
//       headers: {
//         'Content-Type': 'application/json',
//       },
//       body: JSON.stringify({
//         email: email,
//         password: password,
//       }),
//     });

//     if (response.ok) {
//       const data = await response.text(); // Or use `response.json()` if backend returns JSON
//       alert(data + " : " + email);
//       const token = response.data.token;
//       // Store token in localStorage
//       localStorage.setItem('token', token);
//     } else {
//       const error = await response.text();
//       alert("Login failed: " + error);
//     }
//   } catch (err) {
//     console.error("Login error", err);
//     alert("Login error: " + err.message);
//   }
// };

//Method(3)
const handleLogin = async (e) => {
  e.preventDefault();

  try {
    const response = await login(email, password);

    if (response.data && response.data.token) {
      const token = response.data.token;
      localStorage.setItem('token', token);
      console.log("Token :", token);
      alert("Login successful!");
      // Optionally redirect: navigate("/dashboard");
    } else {
      console.error("Token missing in response", response);
      alert("Login failed: token missing from server response");
    }

  } catch (error) {
    console.error("Login error", error);
    if (error.response?.status === 401 || error.response?.status === 403) {
      alert("Invalid email or password");
    } else {
      alert("Login failed: " + error.message);
    }
  }
};


  return (
    <div>
      <h2>Login</h2>
      <form onSubmit={handleLogin}>
        <input type="email" placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} required /><br />
        <input type="password" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)} required /><br />
        <button type="submit">Login</button>
      </form>
      <p><a href="/signup">Sign Up</a> | <a href="/forgot-password">Forgot Password?</a></p>
    </div>
  );
}