import React, { useState } from 'react';
import axios from 'axios';
import { useDispatch } from 'react-redux';
import { loginSuccess, loginFailure, setLoading } from '../store/store';

export default function LoginForm() {
  const [form, setForm] = useState({ uEmail: '', uPassword: '' });
  const dispatch = useDispatch();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    dispatch(setLoading(true)); // 로딩 상태 시작
    
    try {
      const response = await axios.post('/users/login', form, { withCredentials: true });
      // 로그인 성공 시 유저 정보를 스토어에 저장
      dispatch(loginSuccess({
        email: form.uEmail,
        ...response.data // 서버에서 받은 추가 유저 정보
      }));
      alert('로그인 성공!');
    } catch (error) {
      // 로그인 실패 시 에러 정보를 스토어에 저장
      dispatch(loginFailure(error.response?.data?.message || '로그인에 실패했습니다.'));
      alert('로그인 실패');
    } finally {
      dispatch(setLoading(false)); // 로딩 상태 종료
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