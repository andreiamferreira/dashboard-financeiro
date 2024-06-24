export type AccountTypes = {
    institution: string;
    type: string;
    balance: number;
}

export interface ITableAccount {
    onEditAccount: () => void;
    accounts: Account[];
    fetchAccounts: () => void;
}

export interface Account {
    id: number;
    accountType: string;
    institution: string;
    balance: number;
  }