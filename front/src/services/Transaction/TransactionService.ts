import { CreateTransactionProps, UpdateTransaction } from "../../components/TransactionModal/Types/TransactionProps";
import api from "../api";

export const addTransaction = async (data: CreateTransactionProps) => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        const response = await api.post(
            "/transactions/add",
            {
                transactionDate: data.transactionDate,
                amount: data.amount,
                transactionType: data.type,
                category: data.category,
                bankAccId: data.bankAccId
            },
            {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
            }
        );
        
        return response.data;
    } catch (error) {
        console.error("Error:", error);
        throw error;
    }
};

export const updateTransaction = async (data: UpdateTransaction) => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        console.log(data.id)
        const response = await api.put(
            `/transactions/update/${data.id}`,
            {
                transactionDate: data.transactionDate,
                amount: data.amount,
                bankAccId: data.bankAccId
            },
            {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
            }
        );
        
        return response.data;
    } catch (error) {
        console.error("Error:", error);
        throw error;
    }
};

export const deleteTransaction = async (id: number) => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        console.log(id)
        const response = await api.delete(
            `/transactions/delete/${id}`,
            {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
            }
        );
        
        return response.data;
    } catch (error) {
        console.error("Error:", error);
        throw error;
    }
};

export const getAllTransactions = async (accountId: number) => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        try {
            const response = await api.get(
                `/transactions/account/${accountId}`,
                {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                }
            );            return response.data;
          } catch (error) {
            console.error('Error fetching all accounts:', error);
            throw error;
          }
    } catch (error) {
        console.error("Error:", error);
        throw error;
    }
};