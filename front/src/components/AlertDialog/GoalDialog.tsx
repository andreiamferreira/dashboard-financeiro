import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import React from 'react'
import { IconButton } from '@mui/material';
import { IGoalDialog } from './Types/GoalDialogProps';
import { MdOutlineDescription } from "react-icons/md";


const GoalDialog: React.FC<IGoalDialog> = ({ alertText, title, description, amount, limitDate }) => {
 
  const [open, setOpen] = React.useState<boolean>(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };   


  return (
    <>
      <IconButton onClick={handleClickOpen} style={{ verticalAlign: 'middle' }} >
      <MdOutlineDescription style={{color: '#FD6B6B', verticalAlign: 'middle' }}/>
      </IconButton>
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
            <b>Título: </b> {title} <br />
            <b>Descrição:</b> {description} <br />
            <b>Valor:</b> {amount} <br />
            <b>Data limite:</b> {limitDate} <br />
            </DialogContentText>
        </DialogContent>
        <DialogActions style={{ justifyContent: 'center' }}>
          <Button onClick={handleClose} style={{ color: 'black' }}>
            Fechar
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}

export default GoalDialog;