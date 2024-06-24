import { CreateAccount, UpdateAcount } from "../../components/AccountModal/Types/ModalProps";
import api from "../api";

export const addAccount = async (data: CreateAccount) => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        const response = await api.post(
            "/accounts/add",
            {
                accountType: data.type,
                institution: data.institution,
                balance: data.balance,
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

export const updateAccount = async (data: UpdateAcount) => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        console.log(data.id)
        const response = await api.put(
            `/accounts/update/${data.id}`,
            {
                accountType: data.type,
                institution: data.institution,
                balance: data.balance,
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

export const deleteAccount = async (id: number) => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        console.log(id)
        const response = await api.delete(
            `/accounts/delete/${id}`,
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

export const getAllAccountsByUser = async () => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        try {
            const response = await api.get(
                "/accounts/all",
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