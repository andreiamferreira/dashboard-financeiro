import Stack from '@mui/material/Stack';
import Paper from '@mui/material/Paper';
import { styled } from '@mui/material/styles';
import { useEffect, useState } from 'react';
import { getAllAccountsByUser } from '../../services/Account/AccountController';
import { Account } from '../AccountModal/Types/ModalProps';
import { Transaction } from '../TransactionModal/Types/TransactionProps';
import { getAllTransactionsByUser } from '../../services/Dashboard/DashboardService';

const DemoPaper = styled(Paper)(({ theme }) => ({
  width: 200,
  height: 120,
  padding: theme.spacing(2),
  ...theme.typography.body2,
  textAlign: 'center',
  fontSize: '20px',
}));

export default function PaperComponent() {

  const [accounts, setAccounts] = useState<Account[]>([]);
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [totalBalance, setTotalBalance] = useState<number>(0);


  useEffect(() => {
    const fetchData = async () => {
      try {

        const accounts = await getAllAccountsByUser();
        setAccounts(accounts);

        const transactions = await getAllTransactionsByUser();
        setTransactions(transactions);

        const balanceSum = accounts.reduce((acc: number, account: { balance: string; }) => acc + parseFloat(account.balance), 0);
        setTotalBalance(balanceSum);

      } catch (error) {
        console.error("Error:", error);
      }
    };

    fetchData();
  }, []);

  const numberOfAccounts = accounts.length;
  const numberOfTransactions = transactions.length;

  return (
    <Stack direction="row" spacing={2}>
      <DemoPaper variant="elevation"><b>Saldo Total</b> 
      <br/> <br/>
      R${totalBalance.toFixed(2)}</DemoPaper>
      <DemoPaper variant="elevation"><b>Contas cadastradas</b>
      <br/> <br/>
      {numberOfAccounts}</DemoPaper>
      <DemoPaper variant="elevation"><b>Transações totais</b>
      <br/> <br/>      
      {numberOfTransactions}
      </DemoPaper>
    </Stack>
  );
}
