export type CreateUser = {
    name: string;
    email: string;
    cpf: string;
    password: string;
}

export interface CreateUserFormProps {
    onSubmit: (payload: CreateUser) => void;
}