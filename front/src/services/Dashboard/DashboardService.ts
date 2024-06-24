import api from "../api";

export const getTransactionTypes = async () => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        try {
            const response = await api.get(
                "/transactions/types",
                {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                }
            );            return response.data;
          } catch (error) {
            console.error('Error fetching used categories:', error);
            throw error;
          }
    } catch (error) {
        console.error("Error:", error);
        throw error;
    }
};

export const getCategoriesUsed = async () => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        try {
            const response = await api.get(
                "/transactions/categories",
                {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                }
            );            return response.data;
          } catch (error) {
            console.error('Error fetching used categories:', error);
            throw error;
          }
    } catch (error) {
        console.error("Error:", error);
        throw error;
    }
};

export const getTransactionsMonthlySummary = async () => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        try {
            const response = await api.get(
                "/transactions/inout",
                {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                }
            );            return response.data;
          } catch (error) {
            console.error('Error fetching used categories:', error);
            throw error;
          }
    } catch (error) {
        console.error("Error:", error);
        throw error;
    }
};

export const getTransactionsTypeMonthlySummary = async () => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        try {
            const response = await api.get(
                "/transactions/types/monthly",
                {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                }
            );            return response.data;
          } catch (error) {
            console.error('Error fetching used categories:', error);
            throw error;
          }
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
            console.error('Error fetching users accounts', error);
            throw error;
          }
    } catch (error) {
        console.error("Error:", error);
        throw error;
    }
};

export const getAllTransactionsByUser = async () => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        try {
            const response = await api.get(
                "/transactions/all",
                {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                }
            );            return response.data;
          } catch (error) {
            console.error('Error fetching users transactions', error);
            throw error;
          }
    } catch (error) {
        console.error("Error:", error);
        throw error;
    }
};