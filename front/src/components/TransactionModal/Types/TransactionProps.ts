
export interface CategoryType {
    value: string;
    label: string;
}

export interface TransactionTypeType {
    value: string;
    label: string;
}

export type CreateTransactionProps = {
    category: string;
    type: string;
    amount: number;
    transactionDate: Date | null | undefined;
    bankAccId: number;
}

export type UpdateTransaction = {
    id: number;
    bankAccId: number;
    amount: number;
    transactionDate: Date | null | undefined;
    category?: string;
    type?: string;
}

export type Account = {
    institution: string;
    accountType: string;
    balance: number;
    id: number;
}

export type Transaction = {
    id: number;
    category: string;
    transactionType: string;
    amount: number;
    transactionDate: Date | null | undefined;
    bankAccId: number;
}

export interface ICreateTransactionProps {
    onSubmit: (payload: CreateTransactionProps) => void;
    open: boolean;
    setModalOpen?: () => void;
    title?: string;
    subtitle?: string;
    handleClose:() => void;
    handleOpen?: () => void;
    isEditing: boolean;
    accountData: Account;
}

export interface IUpdateTransactionProps {
    onSubmit: (payload: UpdateTransaction) => void;
    open: boolean;
    setModalOpen?: () => void;
    title?: string;
    subtitle?: string;
    handleClose:() => void;
    handleOpen?: () => void;
    isEditing: boolean;
    transactionData: Transaction;
}