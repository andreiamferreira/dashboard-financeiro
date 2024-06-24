import { useState } from 'react';
import Navbar from '../../components/Navbar/Navbar';
import Sidebar from '../../components/Sidebar/Sidebar';
import '../Style.css';
import { UpdateTransaction } from '../../components/TransactionModal/Types/TransactionProps';
import TransactionTable from '../../components/Tables/TransactionTable/TransactionTable';
import { ToastContainer } from 'react-toastify';

function TransactionPage() {
  const [, setOpenModal] = useState<boolean>(false);

  const handleEditAccount = () => {
    setOpenModal(true);
  };

  const handleCreateTransaction = (e: UpdateTransaction) => {
    alert(JSON.stringify(e));
    console.log('Form submitted');
  };
 

  return (
    <>
      <Navbar />
      <Sidebar />
      <div className='container'>
      <TransactionTable
        onEditAccount={handleEditAccount}
        onSubmit={handleCreateTransaction}
      />
    </div>
    <ToastContainer />

    </>

  );
}

export default TransactionPage;
