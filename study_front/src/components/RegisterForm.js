import React, { useState } from 'react';
import axios from 'axios';

export default function RegisterForm() {
  const [form, setForm] = useState({ uEmail: '', uPassword: '', uName: '' });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('/users/register', form);
      alert('회원가입 성공!');
    } catch (error) {
      alert('회원가입 실패');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input name="uEmail" placeholder="이메일" onChange={handleChange} />
      <input name="uPassword" placeholder="비밀번호" type="password" onChange={handleChange} />
      <input name="uName" placeholder="이름" onChange={handleChange} />
      <button type="submit">회원가입</button>
    </form>
  );
}