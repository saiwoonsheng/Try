import React, { useState } from 'react';
import { signup } from '../api/auth';

export default function Signup() {
  const [user, setUser] = useState({ username: '', email: '', password: '' });
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
  e.preventDefault();
try {
  await signup(user);
  alert('Signup successful');
} catch (error) {
  if (error.response?.status === 403) {
    const msg = error.response.data.error || "Email already in use";
    alert(msg);
  } else {
    alert("Signup failed.");
  }
}
};

  return (
    <div>
      <h2>Sign Up</h2>
      <form onSubmit={handleSubmit}>
        <input type="text" placeholder="Username" value={user.username} onChange={e => setUser({ ...user, username: e.target.value })} required /><br />
        <input type="email" placeholder="Email" value={user.email} onChange={e => setUser({ ...user, email: e.target.value })} required /><br />
        <input type="password" placeholder="Password" value={user.password} onChange={e => setUser({ ...user, password: e.target.value })} required /><br />
        <button type="submit">Sign Up</button>
      </form>
      {error && <div style={{ color: 'red', marginTop: '10px' }}>{error}</div>}
    </div>
  );
}
