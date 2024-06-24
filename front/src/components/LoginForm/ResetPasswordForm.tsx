import React, { useState } from 'react';
import './ResetPasswordForm.css';
import { FaLock, FaUnlock } from "react-icons/fa";
import { useForm } from "react-hook-form";
import "react-toastify/dist/ReactToastify.css";
import { ToastContainer } from 'react-toastify';
import { IResetPasswordProps, ResetPasswordProps } from './Types/ResetPasswordProps';

const ResetPasswordForm: React.FC<IResetPasswordProps> = ({ onSubmit }) => {
    const { register, handleSubmit, watch } = useForm<ResetPasswordProps>();
    const [showPassword, setShowPassword] = useState<boolean>(false);
    const [showRepeatPassword, setShowRepeatPassword] = useState<boolean>(false);


    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const toggleRepeatPasswordVisibility = () => {
        setShowRepeatPassword(!showRepeatPassword);
    };

    const repeatedPassword = watch("repeatPassword");

    return (
        <div className='wrapper'>
            <form action="" onSubmit={handleSubmit(onSubmit)}>
                <h1>Altere a sua senha</h1>
                <div className='input-box'>
                    <input
                        type={showPassword ? "text" : "password"}
                        placeholder='Senha'
                        id="password"
                        min={"6"}
                        {...register('password', { required: true })}
                        required/>
                    {showPassword ? <FaUnlock className='icon' onClick={togglePasswordVisibility}/> : <FaLock className='icon' onClick={togglePasswordVisibility}/>}
                </div>
                <div className='input-box'>
                    <input
                        type={showRepeatPassword ? "text" : "password"}
                        placeholder='Repita a sua senha'
                        id="repeatPassword"
                        min={"6"}
                        {...register('repeatPassword', { required: true })}
                        required/>
                    {showRepeatPassword ? <FaUnlock className='icon' onClick={toggleRepeatPasswordVisibility}/> : <FaLock className='icon' onClick={toggleRepeatPasswordVisibility}/>}
                </div>
                {repeatedPassword !== watch("password") && (
                    <div className="error-wrapper">
                        <p className="error-message">As senhas não coincidem.</p>
                    </div>                )}
                <button type="submit" disabled={repeatedPassword !== watch("password")}>Alterar senha</button>
                <ToastContainer /> 
            </form>
        </div>
    );
}

export default ResetPasswordForm;
