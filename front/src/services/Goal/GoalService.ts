import { CompleteGoalProps, CreateGoalProps, UpdateGoalProps } from "../../components/GoalModal/types/GoalProps";
import api from "../api";

export const addGoal = async (data: CreateGoalProps) => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        const response = await api.post(
            "/goals/add",
            {
                amount: data.amount,
                description: data.description,
                limitDate: data.limitDate
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

export const updateGoal = async (data: UpdateGoalProps) => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        const response = await api.put(
            `/goals/update/${data.id}`,
            {
                amount: data.amount,
                description: data.description,
                limitDate: data.limitDate,
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

export const completeGoal = async (data: CompleteGoalProps) => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        const response = await api.put(
            `/goals/complete-goal/${data.id}`,
            {
                isCompleted: data.isCompleted,
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

export const deleteGoal = async (id: number) => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        console.log(id)
        const response = await api.delete(
            `/goals/delete/${id}`,
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

export const getAllGoals = async () => {
    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token not found");
        }
        try {
            const response = await api.get(
                "/goals",
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