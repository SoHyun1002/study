import { configureStore } from '@reduxjs/toolkit';
import userSlice from './authSlice';

// 리덕스 스토어 생성
const store = configureStore({
  reducer: {
    user: userSlice, // 유저 슬라이스의 리듀서 등록
  },
});

export default store;
