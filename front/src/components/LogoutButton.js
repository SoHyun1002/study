import React from 'react';
import axios from 'axios';

export default function LogoutButton() {
  const handleLogout = async () => {
    try {
      await axios.post('/users/logout', {}, { withCredentials: true });
      alert('로그아웃 완료');
    } catch (error) {
      alert('로그아웃 실패');
    }
  };

  return <button onClick={handleLogout}>로그아웃</button>;
}