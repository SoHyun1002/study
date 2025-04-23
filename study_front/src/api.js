import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8921/users',
  withCredentials: true, // 쿠키 포함
});

export default api;