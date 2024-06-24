import { Goal } from "../../Tables/GoalTable/Types/GoalTable";

export type CreateGoalProps = {
    amount: number;
    description: string;
    limitDate: Date | null | undefined;
}

export type UpdateGoalProps = {
    id: number;
    amount: number;
    description: string;
    limitDate: Date | null | undefined;
}

export type CompleteGoalProps = {
    id: number;
    isCompleted: boolean;
}

export interface ICreateGoalProps {
    onSubmit: (payload: CreateGoalProps) => void;
    open: boolean;
    setModalOpen?: () => void;
    title?: string;
    subtitle?: string;
    handleClose:() => void;
    handleOpen?: () => void;
    isEditing: boolean;
}

export interface IUpdateGoalProps {
    onSubmit: (payload: UpdateGoalProps) => void;
    open: boolean;
    setModalOpen?: () => void;
    title?: string;
    subtitle?: string;
    handleClose:() => void;
    handleOpen?: (goal: Goal) => void;
    isEditing: boolean;
    goalData: UpdateGoalProps;
}