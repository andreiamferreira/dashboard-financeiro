import React, { useState } from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import { FormControl, IconButton, InputAdornment, InputLabel, OutlinedInput, TextField } from '@mui/material';
import { Close as CloseIcon } from '@mui/icons-material';
import SubmitButton from '../Buttons/SubmitButton';
import { Controller, useForm } from "react-hook-form";
import CleanButton from '../Buttons/CleanButton';
import MyFormHelperText from '../../utils/HelperText';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { CreateGoalProps, ICreateGoalProps } from './types/GoalProps';
import { rowStyle, style } from './GoalModalStyle';
import EditIcon from '@mui/icons-material/Edit';
import { toast } from 'react-toastify';


const GoalModal: React.FC<ICreateGoalProps> = ({ isEditing, onSubmit, title, subtitle}) => {

    const { register, handleSubmit, control} = useForm<CreateGoalProps>();
    const [open, setOpen] = useState(false);

    const [amount, setAmount] = useState<number>(0);
    const [limitDate, setLimitDate] = useState< Date | null | undefined>(null);
    const [description, setDescription] = useState<string>('');

    const handleOpen = (): void => setOpen(true);
    const handleClose = (): void => {
      cleanInput();
      setOpen(false);
    };

    function cleanInput() {
      setAmount(0);
      setLimitDate(null);
      setDescription('');
    }  

    const onSubmitHandler = async (data: CreateGoalProps) => {
      try {
          await onSubmit(data);
          setOpen(false);
          cleanInput();
          toast.success("Meta criada com sucesso!");
      } catch (error) {
          console.error('Error submitting form:', error);
          toast.error("Erro ao criar meta")
      }
  };

  return (
    <div>
      {isEditing ? (
        <IconButton onClick={handleOpen} style={{ backgroundColor: '#7C987C', color: 'white' }}>
          <EditIcon fontSize='small'/>
        </IconButton>
      ) : (
        <Button variant="outlined" onClick={handleOpen} style={{ color: '#7C987C', fontSize: '14px', borderColor: '#7C987C' }}>Criar nova meta</Button>
      )}    

      <form action="" onSubmit={handleSubmit(onSubmitHandler)}>

      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}   component="form">
        <IconButton aria-label="close" onClick={handleClose} style={{ position: 'absolute', top: '8px', right: '8px' }}>
            <CloseIcon />
          </IconButton>
          <Typography id="modal-modal-title" variant="h4" component="h2" fontWeight={'bold'}>
            {title}
          </Typography>
          <Typography id="modal-modal-description" sx={{ mb: 2 }}>
            {subtitle}
          </Typography>

            <div style={rowStyle}>
            <FormControl style={{ width: '49%' }}>
                <InputLabel htmlFor="outlined-adornment-amount">Valor</InputLabel>
                    <OutlinedInput
                    id="outlined-password-input"
                    label="Valor"
                    type="number"              
                    value={amount.toString()}
                    startAdornment={<InputAdornment position="start">R$</InputAdornment>}
                    {...register('amount')}
                    onChange={event => setAmount(parseFloat(event.target.value))}
                    />
                <MyFormHelperText inputText='Insira o valor que deseja atingir'/>
            </FormControl>

            <FormControl style={{ width: '49%' }}>
            <Controller
           control={control}
           name="limitDate"
           render={({ field }) => (
               <LocalizationProvider dateAdapter={AdapterDateFns}>
                   <DatePicker
                       format="dd-MM-yyyy"
                       label='Data limite'
                       disablePast
                       value={limitDate}
                       defaultValue={null}
                       onChange={(date) => {
                        setLimitDate(date);
                        field.onChange(date || undefined);
                      }}    
                        slotProps={{ field: { shouldRespectLeadingZeros: true } }}
        
                   />
                    <MyFormHelperText inputText='Insira a data limite para completar a meta'/>
               </LocalizationProvider>
           )}
        />
              </FormControl>
        </div>

        <div style={rowStyle}>
            <FormControl style={{ width: '100%' }}>
              <TextField
                id="outlined-multiline-static"
                label="Descrição"
                multiline
                rows={4}
                defaultValue={description}
                {...register('description')}
                onChange={event => setDescription(event.target.value)}
                />
                  <MyFormHelperText inputText='Insira uma breve descrição da sua meta'/>
              </FormControl>
            </div>

          <div style={{ display: 'flex', justifyContent: 'center' }}>
           <SubmitButton buttonTitle='Enviar'/>
           <div style={{ width: '2%' }} />
           <CleanButton buttonTitle='Limpar' onClick={cleanInput}/>
          </div>
        </Box>
      </Modal>
      </form>
    </div>
  );
}

export default GoalModal;