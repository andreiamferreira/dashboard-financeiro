import { useState } from 'react';
import api from '../../services/api';
import showToastMessage from '../../components/Toast/ErrorToast';
import { useNavigate, useLocation } from 'react-router-dom';
import ResetPasswordForm from '../../components/LoginForm/ResetPasswordForm';
import { ResetPasswordProps } from '../../components/LoginForm/Types/ResetPasswordProps';
import { toast } from 'react-toastify';

function ResetPassword() {
  const navigate = useNavigate();
  const [error, setError] = useState<string | null>(null);

  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const email = searchParams.get('email') || '';

  const handleSubmit = async (data: ResetPasswordProps) => {
    try {
      const response = await api.put(`/reset-password?email=${email}`, {
        email: data.email,
        newPassword: data.password
      }, {
        headers:{
          "Content-Type":"application/json",
        },
      } );
      console.log(response);
      toast.success("Senha atualizada com sucesso! Você será redirecionado para o login.")

      setTimeout(() => {
        navigate("/login");
      }, 2000);

    } catch(error) {
      setError("Please try again.");
      console.log(error);
      showToastMessage("Credenciais inválidas. Tente novamente.");
    }
  };

  return (
    <>
      <ResetPasswordForm onSubmit={handleSubmit} errorMessage={error} />
    </>
  );
}

export default ResetPassword;
