import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import React from 'react'
import { IAlertDialog } from './Types/AlertDialogProps';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import { IconButton } from '@mui/material';


const AlertDialog: React.FC<IAlertDialog> = ({ alertText, isDeleting, onDelete, itemId }) => {
 
  const [open, setOpen] = React.useState<boolean>(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };   

  const handleDeleteClick = () => {
    onDelete(itemId);
    handleClose();
  };

  return (
    <>
    {isDeleting ? (
      <IconButton onClick={handleClickOpen} style={{ verticalAlign: 'middle' }} >
      <DeleteForeverIcon fontSize='large' style={{color: '#FD6B6B', verticalAlign: 'middle' }}/>
      </IconButton>
    ) : (
      <Button variant="outlined" onClick={handleClickOpen}>Open alert dialog</Button>
    )}
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title" style={{ alignItems: 'center' }}>
          {alertText}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description" style={{ alignItems: 'center' }}>
            Essa ação não poderá ser desfeita!
          </DialogContentText>
        </DialogContent>
        <DialogActions style={{ justifyContent: 'center' }}>
          <Button onClick={handleDeleteClick} autoFocus style={{ color: 'white', backgroundColor: '#FD6B6B' }}>Excluir</Button>
          <Button onClick={handleClose} style={{ color: 'black' }}>
            Fechar
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}

export default AlertDialog;