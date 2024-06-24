import React, { useState } from 'react';
import './LoginForm.css';
import { FaSpinner } from "react-icons/fa";
import { MdEmail } from "react-icons/md";
import { useForm } from "react-hook-form";
import "react-toastify/dist/ReactToastify.css"
import { ToastContainer, toast } from 'react-toastify';
import api from '../../services/api';

const ForgotPasswordForm: React.FC = () => {
    const { register, handleSubmit } = useForm();
    const [email, setEmail] = useState<string>('');
    const [loading, setLoading] = useState<boolean>(false);
    const [success, setSuccess] = useState<boolean>(false);

    const handleForgotPassword = async () => {
        setLoading(true);
        try {
            const response = await api.post(`/forgot-password?email=${email}`);

            if (response.status === 200) {
                setSuccess(true);
                toast.success('Instruções enviadas para o seu email');
            } else {
                toast.error('Erro! Tente novamente');
            }
        } catch (error) {
            console.error('Error:', error);
            toast.error('Erro! Tente novamente');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className='wrapper'>
            <form action="" onSubmit={handleSubmit(handleForgotPassword)}>
                {success ? (
                  <div>
                      <h1>E-mail enviado!</h1>
                        <p style={{ textAlign: 'center' }}>As instruções para atualizar a sua senha foram enviadas para o seu e-mail.</p>
                    </div>
                ) : (
                    <div>
                      <h1>Digite seu e-mail</h1>
                        <div className="input-box">
                            <input
                                type="text"
                                placeholder="E-mail"
                                value={email}
                                {...register('email', { required: true })}
                                onChange={event => setEmail(event.target.value)}
                                required />
                            <MdEmail className='email-icon' />
                        </div>
                        <button type="submit" disabled={loading || email.trim() === ''}>
                            {loading ? <FaSpinner className='spinner' /> : 'Enviar'}
                        </button>
                    </div>
                )}
                <ToastContainer />
            </form>
        </div>
    )
}

export default ForgotPasswordForm;
