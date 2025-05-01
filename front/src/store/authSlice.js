// // 유저 정보를 관리하는 슬라이스 생성
// const userSlice = createSlice({
//     name: 'user', // 슬라이스의 이름
//     initialState: {
//       // 초기 상태
//       userInfo: null, // 유저 정보
//       isLoggedIn: false, // 로그인 상태
//       loading: false, // 로딩 상태
//       error: null, // 에러 정보
//     },
//     reducers: {
//       // 로그인 성공 시 유저 정보 저장
//       loginSuccess: (state, action) => {
//         state.userInfo = action.payload;
//         state.isLoggedIn = true;
//         state.loading = false;
//         state.error = null;
//       },
//       // 로그인 실패 시 에러 정보 저장
//       loginFailure: (state, action) => {
//         state.userInfo = null;
//         state.isLoggedIn = false;
//         state.loading = false;
//         state.error = action.payload;
//       },
//       // 로그아웃 시 상태 초기화
//       logout: (state) => {
//         state.userInfo = null;
//         state.isLoggedIn = false;
//         state.loading = false;
//         state.error = null;
//       },
//       // 로딩 상태 설정
//       setLoading: (state, action) => {
//         state.loading = action.payload;
//       },
//     },
//   });
  
//   // 액션 생성자 내보내기
//   export const { loginSuccess, loginFailure, logout, setLoading } = userSlice.actions;