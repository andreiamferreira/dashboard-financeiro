import { useNavigate } from 'react-router-dom';
import LoginForm from '../../components/LoginForm/LoginForm';
import api from '../../services/api';
import { LoginUserProps } from '../../components/LoginForm/Types/LoginProps';
import { useState } from 'react';
import showToastMessage from '../../components/Toast/ErrorToast';


function LoginPage() {

  const navigate = useNavigate();
  const [error, setError] = useState<string | null>(null);
  

    const handleSubmit = async (data: LoginUserProps) => {
      
      try{
        const response = await api.post("/auth/login", {
          email: data.email,
          password: data.password
        }, {
          headers:{
            "Content-Type":"application/json",
          },
        } );
        console.log(response);
        const token = response.data.token;
        localStorage.setItem("token", token);

        navigate("/home");
       
      }catch(error){
        setError("Invalid email or password. Please try again.");
        console.log(error);
        showToastMessage("Credenciais inválidas Tente novamente.");

      }
    
      console.log(data);
    };

return (
  <>   
    <LoginForm onSubmit={handleSubmit} errorMessage={error}/>
  </>
  );
}

export default LoginPage;
