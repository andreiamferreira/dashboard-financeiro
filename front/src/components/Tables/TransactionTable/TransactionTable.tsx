import { Box } from "@mui/material";
import './TransactionTable.css';
import { useEffect, useState } from "react";
import AlertDialog from "../../AlertDialog/AlertDialog";
import { ITransactionTable, Transaction } from "./Types/TransactionTableProps";
import UpdateTransactionModal from "../../TransactionModal/UpdateTransactionModal";
import { deleteTransaction, getAllTransactions, updateTransaction } from "../../../services/Transaction/TransactionService";
import { toast } from "react-toastify";
import { useParams } from "react-router-dom";
import { UpdateTransaction } from "../../TransactionModal/Types/TransactionProps";
import { formatEnumValue } from "../../../utils/FormatEnums";
import { isIn } from "../../../utils/IsCategoryInOrOut";

const tableStyle: React.CSSProperties = {
      borderCollapse: 'separate',
      width: '100%',
      borderSpacing: '0 15px',
      marginRight: '5rem',
      marginLeft: '13rem', 
      overflowY: 'auto',
      maxHeight: 'calc(100vh - 200px)'
  };  


const TransactionTable: React.FC<ITransactionTable> = () => {
  const { id } = useParams<{ id: string }>();

  const [openModal, setOpenModal] = useState<boolean>(false);
  const handleClose = (): void => setOpenModal(false);
  const handleOpen = (): void => setOpenModal(true);

  const [transactions, setTransactions] = useState<Transaction[]>([]);

  const fetchTransactions = async (accountId: number) => {
    try {
      const transactionsData = await getAllTransactions(accountId);
      setTransactions(transactionsData);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  useEffect(() => {
    if (id) {
      fetchTransactions(parseInt(id, 10));
    }
  }, [id]);

  const handleDelete = async (id: number) => {
    try {
        await deleteTransaction(id);
        toast.success("Transação excluida com sucesso!");
        if (id) {
          fetchTransactions(id);
        }
    } catch (error) {
        toast.error("Erro! Tente novamente.");
        console.error("Error:", error);
    }
  };

  const handleUpdateTransaction = async (data: UpdateTransaction & { id: number }) => {
    console.log(data.id)
    try {
        await updateTransaction({ 
            id: data.id,
            transactionDate: data.transactionDate,
            amount: data.amount,
            bankAccId: data.bankAccId
        });
        toast.success("Transação editada com sucesso!");
        if (id) {
          fetchTransactions(parseInt(id, 10));
        }
    } catch (error) {
        toast.error("Erro! Tente novamente.");
        console.error("Error:", error);
    }
}
    const rows = transactions.map(item => (
        <tr key={item.id} style={{ marginBottom: '10px', marginTop: '10px' }}>
            <td
              style={{ 
                color: isIn(item.category as string) ? 'green' : 'red', 
                textDecoration: 'none', 
                fontWeight: 'bold' 
                    }}>
            {formatEnumValue(item.category)} 

            </td>
            <td style={{fontWeight: 'bold'}}>{formatEnumValue(item.transactionType)}</td>
            <td
              style={{ 
                color: isIn(item.category as string) ? 'green' : 'red', 
                textDecoration: 'none', 
                fontWeight: 'bold' 
                    }}>
           {isIn(item.category as string) ? '+' : '-'}R${item.amount.toFixed(2)}</td>
           <td >   
            <div style={{ display: 'flex', justifyContent: 'space-around', alignItems: 'center' }}>
            <UpdateTransactionModal 
              handleOpen={handleOpen}
              isEditing={true}
              open={openModal}
              handleClose={handleClose}
              onSubmit={handleUpdateTransaction}
              transactionData={item}
              title='Editar'
              subtitle='Edite a sua transação'/> 
            <AlertDialog 
              alertText='Tem certeza que quer excluir esta transação?' 
              isDeleting={true} 
              onDelete={handleDelete} 
              itemId={item.id}/>
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

export default TransactionTable;
