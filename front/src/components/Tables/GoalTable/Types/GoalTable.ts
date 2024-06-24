import { CreateGoalProps } from "../../../GoalModal/types/GoalProps";

export interface ITableGoal {
    onEditAccount: () => void;
    onSubmit: (payload: CreateGoalProps) => void;
    goals: Goal[];
    fetchGoals: () => void;
}

export interface Goal {
    isCompleted: boolean | undefined;
    id: number;
    amount: number;
    description: string;
    limitDate: Date | null | undefined;
    isOverdue?: boolean;
  }

  