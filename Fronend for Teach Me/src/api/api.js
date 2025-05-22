// api.js or wherever you define the Axios instance
import axios from 'axios';

const API = axios.create({
  baseURL: 'http://localhost:8080/auth',
  withCredentials: true, // 👈 This is required to send cookies like JSESSIONID
});

API.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}); 

export default API;
