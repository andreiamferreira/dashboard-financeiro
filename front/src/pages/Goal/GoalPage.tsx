import { useEffect, useState } from 'react';
import Navbar from '../../components/Navbar/Navbar';
import Sidebar from '../../components/Sidebar/Sidebar';
import '../Style.css';
import GoalModal from '../../components/GoalModal/GoalModal';
import { CreateGoalProps } from '../../components/GoalModal/types/GoalProps';
import GoalTable from '../../components/Tables/GoalTable/GoalTable';
import { addGoal, getAllGoals } from '../../services/Goal/GoalService';
import { ToastContainer, toast } from 'react-toastify';
import { Goal } from '../../components/Tables/GoalTable/Types/GoalTable';

function GoalPage() {
  const [openModal, setOpenModal] = useState<boolean>(false);
  const [goals, setGoals] = useState<Goal[]>([]);
  const handleClose = (): void => setOpenModal(false);
  const handleOpen = (): void => setOpenModal(true);

  const handleEditAccount = () => {
    setOpenModal(true);
  };

  const fetchGoals = async () => {
    try {
      const goalsData = await getAllGoals();
      setGoals(goalsData);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  useEffect(() => {
    fetchGoals();
  }, []);


  const handleSubmit = async (data: CreateGoalProps) => {
    try {
      await addGoal({
        amount: data.amount,
        description: data.description,
        limitDate: data.limitDate,
      });
      fetchGoals();
      handleClose();
    } catch (error) {
      toast.error("Erro! Tente novamente.");
      console.error("Error:", error);
    }
  };

  return (
    <>
      <Navbar />
      <Sidebar />
      
      <div className="container">        
        <div style={{ position: 'absolute', top: '100px', right: '50px' }}>
        <GoalModal
          open={openModal}
          handleClose={handleClose}
          title='Criar uma meta' 
          subtitle='Crie uma meta'
          onSubmit={handleSubmit}
          isEditing={false}
          handleOpen={handleOpen}
      />
      </div>
          <GoalTable 
          onEditAccount={handleEditAccount} 
          onSubmit={handleSubmit} 
          fetchGoals={fetchGoals} 
          goals={goals}/>
        </div>
        <ToastContainer />

    </>
  );
}

export default GoalPage;

