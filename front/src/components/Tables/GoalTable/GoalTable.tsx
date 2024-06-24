import { Box, Checkbox, InputLabel } from "@mui/material";
import './GoalTable.css';
import { useEffect, useState } from "react";
import AlertDialog from "../../AlertDialog/AlertDialog";
import { ITableGoal } from "./Types/GoalTable";
import GoalDialog from "../../AlertDialog/GoalDialog";
import { completeGoal, deleteGoal, updateGoal } from "../../../services/Goal/GoalService";
import { toast } from "react-toastify";
import UpdateGoalModal from "../../GoalModal/UpdateGoalModal";
import { UpdateGoalProps } from "../../GoalModal/types/GoalProps";


const tableStyle: React.CSSProperties = {
  borderCollapse: 'separate',
  width: '100%',
  borderSpacing: '0 15px',
  marginRight: '5rem',
  marginLeft: '13rem', 
  overflowY: 'auto',
  maxHeight: 'calc(100vh - 200px)'
};  


const GoalTable: React.FC<ITableGoal> = ({ goals, fetchGoals }) => {

  const [openModal, setOpenModal] = useState<boolean>(false);
  const handleClose = (): void => setOpenModal(false);
  const handleOpen = (): void => setOpenModal(true);

  useEffect(() => {
    fetchGoals();
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const handleUpdateSubmit = async (data: UpdateGoalProps) => {
    try {
        await updateGoal({ 
            amount: data.amount,
            description: data.description,
            limitDate: data.limitDate,
            id: data.id
        });
        toast.success("Meta atualizada com sucesso!");
        fetchGoals();
    } catch (error) {
        toast.error("Erro! Tente novamente.");
        console.error("Error:", error);
    }
};

  const handleDelete = async (id: number) => {
    try {
        await deleteGoal(id); 
        toast.success("Meta excluida com sucesso!") 
        fetchGoals();
    } catch (error) {
        toast.error("Erro! Tente novamente.");
        console.error("Error:", error);
    }
  };

  const handleCheckboxChange = async (id: number, isChecked: boolean) => {
    try {
        await completeGoal({ id, isCompleted: isChecked });
        toast.success(`Parabéns! Você concluiu a Meta #${id}!`);
        fetchGoals();
    } catch (error) {
        toast.error("Erro! Tente novamente.");
        console.error("Error:", error);
    }
  };

    const rows = goals.map(item => (
        <tr key={item.id} style={{ marginBottom: '10px', marginTop: '10px' }}>
            <td>
              <Checkbox
                color="success"
                checked={item.isCompleted}
                onChange={(e) => handleCheckboxChange(item.id, e.target.checked)}
              /> 
              Meta #{item.id}</td>

            <td>R${item.amount.toFixed(2)}</td>
            <td>
              {item.limitDate ? new Date(item.limitDate).toLocaleDateString() : ''}
            </td>           
            <td>
              <div style={{ display: 'flex', justifyContent: 'space-around', alignItems: 'center' }}>
            <GoalDialog
              alertText={`Meta #${item.id}`} 
              title={`Meta #${item.id}`}  
              description={item.description} 
              amount={`R$ ${item.amount}`}  
              limitDate={item.limitDate ? new Date(item.limitDate).toLocaleDateString() : ''}/>
            <UpdateGoalModal
              goalData={item}
              handleOpen={handleOpen}
              isEditing={true}
              open={openModal}
              handleClose={handleClose}
              onSubmit={handleUpdateSubmit}
              title='Editar'
              subtitle='Edite a sua transação'/> 
            <AlertDialog 
              alertText='Tem certeza que quer excluir esta meta?'
              isDeleting={true}
              onDelete={handleDelete}
              itemId={item.id}/>
            </div>
            <div>
              
            <InputLabel 
             style={{
              backgroundColor: item.isOverdue ? 'rgba(255, 0, 0, 0.3)' : 'rgba(124,152,124)',
              color: 'white',
              borderRadius: '12px',
              border: '1px solid transparent',           
            }}>{ item.isOverdue ? `Atrasado` : `Em progresso`}</InputLabel>
            </div>

            </td>
        </tr>
    ));

    return (
        <Box sx={{ minWidth: 400 }}>
            <div style={tableStyle}>
                <table className="transaction-table">
                    <tbody>
                        {rows}
                    </tbody>
                </table>
                </div>
        </Box>
    );
}

export default GoalTable;
