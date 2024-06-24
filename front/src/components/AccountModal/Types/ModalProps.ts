

export type CreateAccount = {
    institution: string;
    type: string;
    balance: number;
}


export type UpdateAcount = {
    institution: string;
    type: string;
    balance: number;
    id: number;
}

export type Account = {
    institution: string;
    accountType: string;
    balance: number;
    id: number;
}


export interface ICreateAccountProps {
    onSubmit: (payload: CreateAccount) => void;
    open: boolean;
    setModalOpen?: () => void;
    title?: string;
    subtitle?: string;
    handleClose:() => void;
    handleOpen?: () => void;
    isEditing: boolean;
    id?: number;
}

export interface IUpdateAccountProps {
    onSubmit: (payload: UpdateAcount) => void;
    open: boolean;
    setModalOpen?: () => void;
    title?: string;
    subtitle?: string;
    handleClose:() => void;
    handleOpen?: () => void;
    isEditing: boolean;
    accountData: Account;
}