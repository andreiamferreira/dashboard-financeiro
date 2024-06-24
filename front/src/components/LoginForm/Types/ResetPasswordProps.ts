export type ResetPasswordProps = {
    password: string;
    repeatPassword: string;
    email: string;
}

export interface IResetPasswordProps {
    onSubmit: (payload: ResetPasswordProps) => void;
    errorMessage?: string | null
}