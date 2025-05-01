import React from 'react';
import { useSelector } from 'react-redux';

export default function UserProfile() {
  // useSelector를 사용하여 스토어의 상태를 가져옵니다
  const { userInfo, isLoggedIn, loading } = useSelector((state) => state.user);

  if (loading) {
    return <div>로딩 중...</div>;
  }

  if (!isLoggedIn) {
    return <div>로그인이 필요합니다.</div>;
  }

  return (
    <div>
      <h2>프로필</h2>
      <p>이메일: {userInfo.email}</p>
      {/* 서버에서 받은 추가 유저 정보가 있다면 여기에 표시 */}
      {userInfo.name && <p>이름: {userInfo.name}</p>}
      {userInfo.role && <p>권한: {userInfo.role}</p>}
    </div>
  );
} 