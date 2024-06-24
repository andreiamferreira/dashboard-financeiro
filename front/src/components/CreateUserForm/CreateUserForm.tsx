import React, { useState } from 'react';
import './CreateUserForm.css';
import { FaUser, FaLock } from "react-icons/fa";
import { MdEmail } from "react-icons/md";
import { HiIdentification } from "react-icons/hi2";
import { useForm } from "react-hook-form";
import normalizeCpf from '../../utils/CpfMask';
import { CreateUser, CreateUserFormProps } from './Types/CreateUserFormProps';

const CreateUserForm: React.FC<CreateUserFormProps> = ({ onSubmit }) => {

    const { register, handleSubmit, formState: { errors }} = useForm<CreateUser>();
    const [cpf, setCpf] = useState<string>('');
    const [name, setName] = useState<string>('');
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const inputCpf: string = event.target.value;
        const formattedCpf = normalizeCpf(inputCpf);
        setCpf(formattedCpf);
    };


  return (
    <div className='wrapper'>
        <form action="" onSubmit={handleSubmit(onSubmit)}>
            <h1>Criar conta</h1>
            <div className="input-box">
            <label htmlFor='name' className='input-label'>Nome</label>
                <input
                    type="text"
                    placeholder='Digite seu nome'
                    defaultValue={name}
                    id="name"
                    className={errors.name?.type == "required" ? 'input-error' : ''}
                    {...register('name', {required: true })}
                    onChange={event => setName(event.target.value)}
                />
            <FaUser className='icon'/>
            </div>

            <div className="input-box">
            <label htmlFor='cpf' className='input-label'>CPF</label>
                <input 
                    type="text"
                    placeholder="000.000.000-00"
                    value={cpf}
                    {...register('cpf', {
                        onChange: handleInputChange,
                        required: true

                    })}
                    name="cpf"
                    id="cpf"
                    maxLength={14}
                    className={errors.cpf?.type == "required" ? 'input-error' : ''}
                />
                <HiIdentification className='icon'/>
            </div>

            <div className="input-box">
            <label htmlFor='email' className='input-label'>E-mail</label>
                <input
                    type="text"
                    placeholder="Digite seu e-mail"
                    defaultValue={email}
                    id="email"
                    {...register('email', { required: true })}
                    onChange={event => setEmail(event.target.value)}
                    minLength={6}
                    className={errors.email?.type == "required" ? 'input-error' : ''}
                />
                <MdEmail className='icon'/>
            </div>

            <div className='input-box'>
            <label htmlFor='password' className='input-label'>Senha</label>
                <input
                    type="password"
                    placeholder='*******************'
                    defaultValue={password} 
                    id="paswword"
                    {...register('password', { required: true })}
                    onChange={event => setPassword(event.target.value)}
                    minLength={6}
                    className={errors.password?.type == "required" ? 'input-error' : ''}
                />
                <FaLock className='icon'/>
            </div>

                <button type="submit" >Cadastrar</button>

            <div className="register-link">
                <p>Já possui uma conta? <a href="/login">Login</a></p>
            </div>
        </form>
    </div>
  )
}

export default CreateUserForm;
