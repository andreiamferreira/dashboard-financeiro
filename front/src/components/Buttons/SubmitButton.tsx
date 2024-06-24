import SendIcon from '@mui/icons-material/Send';
import { Button } from '@mui/material';
import React from 'react';
import { ButtonProps } from './Types/ButtonProps';

const SubmitButton: React.FC<ButtonProps> = ({ buttonTitle }) => {
    return (
    <Button
        type="submit"
        variant="contained"
        sx={{
            width: '50%',
            backgroundColor: '#7C987C',
            borderColor:'#7C987C',
            outline: 'none',
            '&:hover': {
                backgroundColor: '#7C987C',
                borderColor: '7C987C',
                outline: 'none',
            },
            '&:active': { 
                outline: 'none',
            },
            '&:focus': {
                outline: 'none',
            },
        }}
        startIcon={<SendIcon />}
        >
            {buttonTitle}
        </Button>
    )
}

export default SubmitButton;



