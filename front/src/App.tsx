import './App.css';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from './pages/login/Login';
import CreateUserPage from './pages/User/CreateUser';
import AccountPage from './pages/Account/AccountPage';
import TransactionPage from './pages/Transaction/TransactionPage';
import GoalPage from './pages/Goal/GoalPage';
import DashboardPage from './pages/Dashboard/DashboardPage';
import PrivateRoute from './components/PrivateRoute/PrivateRoute';
import ResetPasswordForm from './pages/login/ResetPassword';
import ForgotPasswordForm from './components/LoginForm/ForgotPassword';
import AllTransactionPage from './pages/Transaction/AllTransactionsPage';


function App() {
  return (
    <Router>
      <Routes>
      <Route path="/" element={<Navigate to="/login" />} />
      <Route path="/login" element={<LoginPage />} />
        <Route path="/usuario">
          <Route path="criar" element={<CreateUserPage />} />
        </Route>
        <Route path="/">
          <Route path="/accounts" element={<PrivateRoute Component={AccountPage} />} />
          <Route path="/transactions/:id" element={<TransactionPage />} />
          <Route path="/transactions" element={<AllTransactionPage />} />
          <Route path="/goals" element={<GoalPage />} />
          <Route path="/home" element={<DashboardPage />} />
          <Route path="/forgot-password" element={<ForgotPasswordForm /> }/>
          <Route path="/reset-password" element={<ResetPasswordForm />} />
        </Route>
      </Routes>
    </Router>
  )
}

export default App
