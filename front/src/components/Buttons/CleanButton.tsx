import DeleteIcon from '@mui/icons-material/Delete';
import { Button } from '@mui/material';
import React from 'react';
import { ButtonProps } from './Types/ButtonProps';

const CleanButton: React.FC<ButtonProps> = ({ buttonTitle, onClick }) => {
    return (
    <Button
        variant="outlined"
        sx={{
            width: '50%',
            color: '#7C987C',
            borderColor:'#7C987C',
            outline: 'none',
            '&:hover': {
                outline: '#7C987C',
                borderColor: '#7C987C',
            },
            '&:active': { 
                outline: 'none',
            },
            '&:focus': {
                outline: 'none',
            },
        }}
        onClick={onClick}
        startIcon={<DeleteIcon />}
        >
            {buttonTitle}
        </Button>
    )
}

export default CleanButton;



