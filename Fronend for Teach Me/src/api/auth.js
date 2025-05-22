// import axios from 'axios';

// const API = axios.create({ baseURL: 'http://localhost:8080/auth' });

// export const login = (email, password) =>
//   API.post('/login', { email, password });

// export const signup = (user) =>
//   API.post('/signup', user)
// .then(res => console.log(res.data))
// .catch(err => console.error(err));

// export const forgotPassword = (email) =>
//   API.post('/forgot-password', null, { params: { email } });

// export const resetPassword = (token, newPassword) =>
//   API.post('/reset-password', null, {
//     params: { token, newPassword },
//   });

import API from './api';

export const login = (email, password) =>
  API.post('/login', { email, password });

export const signup = (user) =>
  API.post('/signup', user)
    .then(res => res.data)
    .catch(err => {
      console.error(err);
      throw err; // 🔴 Re-throw the error so the component can catch it
    });


export const forgotPassword = (email) =>
  API.post('/forgot-password', null, { params: { email } });

export const resetPassword = (token, newPassword) =>
  API.post('/reset-password', null, {
    params: { token, newPassword },
  });
