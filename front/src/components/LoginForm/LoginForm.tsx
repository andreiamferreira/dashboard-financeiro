import React, { useState } from 'react';
import './LoginForm.css';
import { FaLock, FaUnlock } from "react-icons/fa";
import { MdEmail } from "react-icons/md";
import { LoginUserFormProps, LoginUserProps } from './Types/LoginProps';
import { useForm } from "react-hook-form";
import { Link } from 'react-router-dom';
import "react-toastify/dist/ReactToastify.css"
import { ToastContainer } from 'react-toastify';

const LoginForm: React.FC<LoginUserFormProps> = ({ onSubmit }) => {
    const { register, handleSubmit } = useForm<LoginUserProps>();
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [showPassword, setShowPassword] = useState<boolean>(false);

    const isFormValid = email.trim() !== '' && password.trim() !== '';


    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };


  return (
    <div className='wrapper'>
    <form action="" onSubmit={handleSubmit(onSubmit)}>
        <h1>Login</h1>
        <div className="input-box">
            <input 
            type="text"
            placeholder="E-mail"
            defaultValue={email} 
            id="email"
            {...register('email', { required: true })}
            onChange={event => setEmail(event.target.value)}
            required/>
            <MdEmail className='email-icon'/>
        </div>

        <div className='input-box'>
            <input
                type={showPassword ? "text" : "password"}
                placeholder='Senha'
                id="password"
                defaultValue={password}
                min={"6"}
                {...register('password', { required: true })}
                onChange={event => setPassword(event.target.value)}
                required/>
            {showPassword ? <FaUnlock className='password-icon' onClick={togglePasswordVisibility}/> : <FaLock className='password-icon' onClick={togglePasswordVisibility}/>}
        </div>

    <div className="remember-forgot">
        <a href="/forgot-password">Esqueci a senha</a>
    </div>

    <button type="submit" disabled={!isFormValid}>Entrar</button>

    <div className="register-link">
        <p>Não possui uma conta? <Link to="/usuario/criar">Criar conta</Link></p>
    </div>
    <ToastContainer /> 
    </form>
    </div>
  )
}

export default LoginForm;
