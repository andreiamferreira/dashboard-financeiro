export type LoginUserProps = {
    email: string;
    password: string;
}

export interface LoginUserFormProps {
    onSubmit: (payload: LoginUserProps) => void;
    errorMessage?: string | null
}