export interface IAlertDialog {
    alertText: string;
    isDeleting: boolean;
    onDelete: (id: number) => void;
    itemId: number;
}