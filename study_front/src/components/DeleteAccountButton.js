import React from 'react';
import axios from 'axios';

const DeleteAccountButton = ({ userId }) => {
  const handleDelete = async () => {
    const confirm = window.confirm('정말로 회원 탈퇴하시겠습니까?');
    if (!confirm) return;

    try {
      await axios.post(`http://localhost:3000/users/delete/${userId}`);
      alert('회원 탈퇴가 완료되었습니다.');
      // 로그아웃 처리나 리디렉션 추가 가능
      window.location.reload();
    } catch (error) {
      console.error('회원 탈퇴 실패:', error);
      alert('회원 탈퇴에 실패했습니다.');
    }
  };

  return <button onClick={handleDelete}>회원 탈퇴</button>;
};

export default DeleteAccountButton;