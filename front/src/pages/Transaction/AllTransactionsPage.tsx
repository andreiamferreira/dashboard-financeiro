import { useState } from 'react';
import Navbar from '../../components/Navbar/Navbar';
import Sidebar from '../../components/Sidebar/Sidebar';
import '../Style.css';
import { UpdateTransaction } from '../../components/TransactionModal/Types/TransactionProps';
import { ToastContainer } from 'react-toastify';
import AllTransactionTable from '../../components/Tables/TransactionTable/AllTransactionsTable';


function AllTransactionPage() {
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
      <AllTransactionTable
        onEditAccount={handleEditAccount}
        onSubmit={handleCreateTransaction}
      />
    </div>
    <ToastContainer />

    </>

  );
}

export default AllTransactionPage;
