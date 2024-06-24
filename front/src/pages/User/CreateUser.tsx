import { CreateUser } from '../../components/CreateUserForm/Types/CreateUserFormProps';
import  CreateUserForm from '../../components/CreateUserForm/CreateUserForm';
import api from '../../services/api';
import { useNavigate } from 'react-router-dom';

function CreateUserPage() {
  const navigate = useNavigate();

    const handleSubmit = async (data: CreateUser) => {
      try{
        const response = await api.post("/users/add", {
          email: data.email,
          password: data.password,
          name: data.name,
          cpf: data.cpf
        }, {
          headers:{
            "Content-Type":"application/json",
          },
        });
        console.log(response);
        navigate("/login");
      }catch(error){
        console.log(error);
      }
        console.log('Form submitted');
    };

    return (
      <>
        <CreateUserForm onSubmit={handleSubmit}/>
      </>
  );
}

export default CreateUserPage;
