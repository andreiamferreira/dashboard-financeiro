import { toast } from "react-toastify";

const showToastMessage = (message: string) => {
    toast.error(message);
  };

export default showToastMessage;