import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import { FormControl, IconButton, InputAdornment, InputLabel, MenuItem, OutlinedInput, TextField } from '@mui/material';
import { Close as CloseIcon } from '@mui/icons-material';
import SubmitButton from '../Buttons/SubmitButton';
import { Controller, useForm } from "react-hook-form";
import CleanButton from '../Buttons/CleanButton';
import MyFormHelperText from '../../utils/HelperText';
import { CategoryType, IUpdateTransactionProps, TransactionTypeType, UpdateTransaction } from './Types/TransactionProps';
import { DatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { rowStyle, style } from './ModalStyle';
import EditIcon from '@mui/icons-material/Edit';


const categories: CategoryType[] = [
    {value: 'RESTAURANTE', label: 'Restaurante'},
    {value: 'MERCADO', label: 'Mercado'},
    {value: 'LAZER', label: 'Lazer'},
    {value: 'FARMACIA', label: 'Farmácia'},
    {value: 'SALARIO', label: 'Salário'},
    {value: 'ACADEMIA', label: 'Academia'},
    {value: 'ASSINATURA', label: 'Assinatura'},
    {value: 'EDUCACAO', label: 'Educação'},
    {value: 'MEDICO', label: 'Médico'},
    {value: 'EMPRESTIMO', label: 'Empréstimo'},
    {value: 'FATURA', label: 'Fatura'},
    {value: 'OUTROS', label: 'Outros'},
];

const types: TransactionTypeType[] = [
    {value: 'CREDITO', label: 'Crédito'},
    {value: 'DEBITO', label: 'Débito'},
    {value: 'PIX', label: 'Pix'},
];

const UpdateTransactionModal: React.FC<IUpdateTransactionProps> = ({ onSubmit, title, subtitle, isEditing, transactionData}) => {

  const { bankAccId } = transactionData;
    const { id } = transactionData;

    const { register, handleSubmit, control} = useForm<UpdateTransaction>();
    const [open, setOpen] = useState(false);
    const handleOpen = (): void => setOpen(true);
    const handleClose = (): void => {
      cleanInput();
      setOpen(false);
    };
    const [amount, setAmount] = useState<number>(0);
    const [selectedCategory, setSelectedCategory] = useState<string | null>('')
    const [selectedType, setSelectedType] = useState<string | null>('')
    const [transactionDate, setTransactionDate] = useState<Date | null | undefined>(null);

    function cleanInput() {
      setSelectedCategory("");
      setSelectedType("");
      setAmount(0);
      setTransactionDate(null);
  }


    useEffect(() => {
      if (transactionData) {
        setSelectedCategory(transactionData.category);
        setSelectedType(transactionData.transactionType);
        setAmount(transactionData.amount);

        if (transactionData.transactionDate) {
          const date = new Date(transactionData.transactionDate);
          setTransactionDate(date);
        } else {
          setTransactionDate(null); 
        }
      }
    }, [transactionData]);

    const onSubmitHandler = async (data: UpdateTransaction) => {
      try {
        await onSubmit({ ...data, bankAccId, id });
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
          <Button variant="outlined" onClick={handleOpen} style={{ color: '#7C987C', fontSize: '12px', borderColor: '#7C987C' }}>Adicionar transação</Button>
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
              <TextField
                id="standard-select-currency"
                select
                label="Tipo"
                defaultValue = {""}
                value={selectedType}
                {...register('type')}
                onChange={event => setSelectedType(event.target.value)}
                helperText="Selecione o tipo da sua transação"
                disabled
              >
                {types.map((type) => (
                  <MenuItem key={type.value} value={type.value}>
                    {type.label}
                  </MenuItem>
                ))}
              </TextField>
            </FormControl>

            <FormControl style={{ width: '49%' }}>
              <TextField
                id="standard-select-currency"
                select
                label="Categoria"
                defaultValue = {""}
                value={selectedCategory}
                {...register('category')}
                onChange={event => setSelectedCategory(event.target.value)}
                helperText="Selecione a categoria da sua transação"
                disabled
              >
                {categories.map((category) => (
                  <MenuItem key={category.value} value={category.value}>
                    {category.label}
                  </MenuItem>
                ))}
              </TextField>
            </FormControl>
            </div>

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
                <MyFormHelperText inputText='Insira o valor da sua transação'/>
            </FormControl>

            <FormControl style={{ width: '49%' }}>
            <Controller
              control={control}
              name="transactionDate"
              render={({ field }) => (
               <LocalizationProvider dateAdapter={AdapterDateFns}>
                   <DatePicker
                       label='Data'
                       value={transactionDate}
                       defaultValue={null}
                       onChange={(date) => {
                        setTransactionDate(date);
                        field.onChange(date || undefined);
                      }}    
                      format="dd-MM-yyyy"
                      slotProps={{ field: { shouldRespectLeadingZeros: true } }}
        
                   />
                    <MyFormHelperText inputText='Insira da tata da sua transação'/>
               </LocalizationProvider>
           )}
        />
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

export default UpdateTransactionModal;