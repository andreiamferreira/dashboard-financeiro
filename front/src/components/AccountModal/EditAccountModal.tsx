import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import { IUpdateAccountProps, UpdateAcount } from './Types/ModalProps';
import MyFormHelperText from '../../utils/HelperText';
import { FormControl, IconButton, InputAdornment, InputLabel, MenuItem, OutlinedInput, TextField } from '@mui/material';
import { Close as CloseIcon } from '@mui/icons-material';
import SubmitButton from '../Buttons/SubmitButton';
import { useForm } from "react-hook-form";
import { AccountType, Institution } from './Types/CreateAccountProps';
import CleanButton from '../Buttons/CleanButton';
import { rowStyle, style } from '../TransactionModal/ModalStyle';
import EditIcon from '@mui/icons-material/Edit';

const institutions: Institution[] = [
    {value: 'BANCO_DO_BRASIL', label: 'Banco do Brasil'},
    {value: 'NUBANK', label: 'Nubank'},
    {value: 'SANTANDER', label: 'Santander'},
    {value: 'CAIXA_ECONOMICA', label: 'Caixa Econômica'},
    {value: 'INTER', label: 'Inter'},
    {value: 'BRADESCO', label: 'Bradesco'},
    {value: 'ITAU', label: 'Itaú'},
    {value: 'BANRISUL', label: 'Banrisul'},
    {value: 'C6', label: 'C6'},
    {value: 'PAGBANK', label: 'PagBank'},
];

const types: AccountType[] = [
  {value: 'POUPANCA', label: 'Poupança'},
  {value: 'CONTA_CORRENTE', label: 'Conta Corrente'},
  {value: 'SALARIO', label: 'Conta Salário'},
];

const EditAccountModal: React.FC<IUpdateAccountProps> = ({ isEditing, onSubmit, title, subtitle, accountData}) => {

    const { id } = accountData;


    const { register, handleSubmit } = useForm<UpdateAcount>();
    const [balance, setBalance] = useState<number>(0);

    const [open, setOpen] = useState(false);
    const [selectedInstitution, setSelectedInstitution] = useState<string | null>('')
    const [selectedType, setSelectedType] = useState<string | null>()
    const handleOpen = (): void => setOpen(true);
    const handleClose = (): void => {
      cleanInput();
      setOpen(false);
    };
    function cleanInput() {
      setSelectedInstitution('');
      setSelectedType('');
      setBalance(0);
  }
  useEffect(() => {
    if (accountData) {
        setSelectedInstitution(accountData.institution);
        setSelectedType(accountData.accountType);
        setBalance(accountData.balance);
    }
}, [accountData]);
 
  const onSubmitHandler = async (data: UpdateAcount) => {
    try {
      await onSubmit({ ...data, id });
      setOpen(false);
      cleanInput();
    } catch (error) {
        console.error('Error submitting form:', error);
    }
};

  return (
    <div>
      {isEditing ? (
        <IconButton onClick={handleOpen} style={{ backgroundColor: '#7C987C', color: 'white' }}>
          <EditIcon fontSize='small'/>
        </IconButton>
      ) : (
        <Button variant="outlined" onClick={handleOpen} style={{ color: '#7C987C', fontSize: '14px', borderColor: '#7C987C' }}>Criar nova conta</Button>
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
            <FormControl style={{ width: '100%' }}>
              <TextField
                id="standard-select-currency"
                select
                label="Instituição"
                value={selectedInstitution}
                defaultValue={''}
                helperText="Selecione a sua instituição"
                {...register('institution')}
                onChange={event => setSelectedInstitution(event.target.value)}

              >
                {institutions.map((institution) => (
                  <MenuItem key={institution.value} value={institution.value}>
                    {institution.label}
                  </MenuItem>
                ))}
              </TextField>
            </FormControl>
          </div>

          <div style={rowStyle}>
            <FormControl style={{ width: '49%' }}>
              <TextField
                id="standard-select-currency"
                select
                label="Tipo"
                value={selectedType}
                defaultValue={''}
                {...register('type')}
                onChange={event => setSelectedType(event.target.value)}
                helperText="Selecione o tipo da sua conta"
              >
                {types.map((type) => (
                  <MenuItem key={type.value} value={type.value}>
                    {type.label}
                  </MenuItem>
                ))}
              </TextField>
            </FormControl>

            <FormControl style={{ width: '49%' }}>
              <InputLabel htmlFor="outlined-adornment-amount">Saldo atual</InputLabel>
              <OutlinedInput
                id="outlined-password-input"
                label="Saldo"
                type="number"
                value={balance.toString()}
                defaultValue={0}
                startAdornment={<InputAdornment position="start">R$</InputAdornment>}
                {... register('balance')}
                onChange={event => setBalance(parseFloat(event.target.value))}
                />
                <MyFormHelperText inputText='Insira seu saldo atual' />
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

export default EditAccountModal;