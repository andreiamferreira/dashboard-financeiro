import { UpdateTransaction } from "../../../TransactionModal/Types/TransactionProps";

export interface ITransactionTable {
    onEditAccount: () => void;
    onSubmit: (payload: UpdateTransaction) => void;
}

export interface Transaction {
    category: string;
    transactionType: string;
    amount: number;
    transactionDate: Date | null | undefined;
    bankAccId: number;
    id: number;
  }