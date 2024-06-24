import { useEffect, useState } from 'react';
import Navbar from '../../components/Navbar/Navbar';
import Sidebar from '../../components/Sidebar/Sidebar';
import '../Style.css';
import { Account, CreateAccount } from '../../components/AccountModal/Types/ModalProps';
import AccountModal from '../../components/AccountModal/AccountModal';
import AccountTable from '../../components/Tables/AccountTable/AccountTable';
import { Typography } from '@mui/material';
import { currentMonth, currentYear } from '../../utils/CurrentDate';
import { addAccount, getAllAccountsByUser } from '../../services/Account/AccountController';
import { ToastContainer, toast } from 'react-toastify';
const titleStyle: React.CSSProperties = {
  marginRight: '5rem',
  marginLeft: '13rem', 
};  

const subtitleStyle: React.CSSProperties = {
  marginLeft: '-12rem', 
  fontSize: '16px',
  marginTop: '3rem'
};  

const month = currentMonth();
const year = currentYear();

function AccountPage() {
  const [openModal, setOpenModal] = useState<boolean>(false);
  const handleClose = (): void => setOpenModal(false);

  const handleEditAccount = (): void => {
    setOpenModal(true);
  };

  const [accounts, setAccounts] = useState<Account[]>([]);

    const fetchAccounts = async () => {
      try {
        const accountsData = await getAllAccountsByUser();
        setAccounts(accountsData);
      } catch (error) {
        console.error("Error:", error);
      }
    };

    useEffect(() => {
      fetchAccounts();
    }, []);
  

  const handleSubmit = async (data: CreateAccount) => {
    try {
      await addAccount({
        type: data.type,
        institution: data.institution,
        balance: data.balance,
      });
      fetchAccounts();
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
      <div className='container'>
      <Typography style={titleStyle} id="modal-modal-title" variant="h4" component="h2" fontWeight={'bold'}>
          Contas
        </Typography>
        <Typography style={subtitleStyle} id="modal-modal-description" sx={{ mb: 2 }}>
          {month}  - {year}
          </Typography>
      <div style={{ position: 'absolute', top: '100px', right: '50px' }}>
      
        <AccountModal
            isEditing={false}
            onSubmit={handleSubmit}
            title="Criar Nova Conta"
            subtitle="Preencha os detalhes da sua nova conta"
            open={openModal}
            handleClose={handleClose}
        />
      </div>
      <AccountTable
        onEditAccount={handleEditAccount}
        fetchAccounts={fetchAccounts}
        accounts={accounts}
      />
      </div>
      <ToastContainer />

    </>
  );
}

export default AccountPage;
