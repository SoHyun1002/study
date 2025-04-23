import React from 'react';
import RegisterForm from './components/RegisterForm';
import LoginForm from './components/LoginForm';
import LogoutButton from './components/LogoutButton';
import DeleteAccountButton from './components/DeleteAccountButton';

function App() {
  const userId = 1; // 👉 실제 앱에서는 localStorage나 context 등에서 로그인된 유저 ID를 받아와야 해!

  return (
    <div style={{ padding: '2rem' }}>
      <h1>간단 인증 예제</h1>
      <RegisterForm />
      <LoginForm />
      <LogoutButton />
      <DeleteAccountButton userId={userId} />
    </div>
  );
}

export default App;