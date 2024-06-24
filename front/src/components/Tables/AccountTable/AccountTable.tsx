import { Box } from "@mui/material";
import { ITableAccount } from "./Types/AccountTableProps";
import './AccountTable.css';
import { useEffect, useState } from "react";
import AlertDialog from "../../AlertDialog/AlertDialog";
import TransactionModal from "../../TransactionModal/CreateTransactionModal";
import { CreateTransactionProps } from "../../TransactionModal/Types/TransactionProps";
import { Link } from "react-router-dom";
import { deleteAccount, updateAccount } from "../../../services/Account/AccountController";
import EditAccountModal from "../../AccountModal/EditAccountModal";
import { toast } from "react-toastify";
import { UpdateAcount } from "../../AccountModal/Types/ModalProps";
import { addTransaction } from "../../../services/Transaction/TransactionService";
import { formatEnumValue } from "../../../utils/FormatEnums";


const tableStyle: React.CSSProperties = {
  borderCollapse: 'separate',
  width: '100%',
  borderSpacing: '0 15px',
  marginRight: '5rem',
  marginLeft: '13rem', 
  overflowY: 'auto',
  maxHeight: 'calc(100vh - 200px)',
};

const AccountTable: React.FC<ITableAccount> = ({ accounts, fetchAccounts }) => {


  const [openModal, setOpenModal] = useState<boolean>(false);
  const handleClose = (): void => setOpenModal(false);
  const handleOpen = (): void => setOpenModal(true);
  
  useEffect(() => {
      fetchAccounts();
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const handleSubmit = async (data: UpdateAcount) => {
    try {
        await updateAccount({ 
            type: data.type,
            institution: data.institution,
            balance: data.balance,
            id: data.id
        });
        toast.success("Conta atualizada com sucesso!");
        fetchAccounts();
    
    } catch (error) {
        toast.error("Erro! Tente novamente.");
        console.error("Error:", error);
    }
};

const handleDelete = async (id: number) => {
  try {
      await deleteAccount(id); 
      toast.success("Conta excluida com sucesso!") 
      fetchAccounts();
  } catch (error) {
      toast.error("Erro! Tente novamente.");
      console.error("Error:", error);
  }
};

    const handleSubmitTransaction = async (data: CreateTransactionProps) => {
      try {
        await addTransaction({ 
          transactionDate: data.transactionDate,
          amount: data.amount,
          type: data.type,
          category: data.category,
          bankAccId: data.bankAccId
        });
        toast.success("Transação adicionada com sucesso!");
        fetchAccounts();
    } catch (error) {
        toast.error("Erro! Tente novamente.");
        console.error("Error:", error);
    }
        };

    const rows = accounts.map(item => (
        <tr key={item.id} style={{ marginBottom: '10px', marginTop: '10px' }}>
        <td>{formatEnumValue(item.accountType)}</td>
        <td>{formatEnumValue(item.institution)}</td>
            <td>
            <Link 
                to={`/transactions/${item.id}`} 
                style={{ 
                    color: item.balance >= 0 ? 'green' : 'red', 
                    textDecoration: 'none', 
                    fontWeight: 'bold' 
                }}
                >
                R${item.balance.toFixed(2)}
                </Link>
            </td>            
            <td >   
            <div style={{ display: 'flex', justifyContent: 'space-around', alignItems: 'center' }}>
            <EditAccountModal 
              handleOpen={handleOpen}
              isEditing={true}
              open={openModal}
              handleClose={handleClose}
              onSubmit={handleSubmit}
              accountData={item}
              title='Editar'
              subtitle='Edite a sua conta'/> 
            <AlertDialog 
              alertText='Tem certeza que quer excluir esta conta?' 
              isDeleting={true} 
              onDelete={handleDelete} 
              itemId={item.id}/>
            </div>
            <div style={{marginTop: '5px', marginBottom: '-5px'}}>
            <TransactionModal 
              onSubmit={handleSubmitTransaction} 
              accountData={item}
              title='Criar transação' 
              subtitle="Edite sua transação" 
              isEditing={false} 
              open={openModal} 
              handleClose={handleClose}/>
            </div>
            </td>
        </tr>
    ));

    return (
        <Box sx={{ minWidth: 400 }}>
          
            <div style={tableStyle}>
                <table className="account-table">
                    <tbody>
                        {rows}
                    </tbody>
                </table>
            </div>
        </Box>
    );
}

export default AccountTable;
