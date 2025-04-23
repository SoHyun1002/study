import React, { useState } from 'react';
import axios from 'axios';

export default function LoginForm() {
  const [form, setForm] = useState({ uEmail: '', uPassword: '' });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      await axios.post('/users/login', form, { withCredentials: true });
      alert('로그인 성공!');
    } catch (error) {
      alert('로그인 실패');
    }
  };

  return (
    <form onSubmit={handleLogin}>
      <input name="uEmail" placeholder="이메일" onChange={handleChange} />
      <input name="uPassword" placeholder="비밀번호" type="password" onChange={handleChange} />
      <button type="submit">로그인</button>
    </form>
  );
}