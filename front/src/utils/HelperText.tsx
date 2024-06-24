import { FormHelperText } from '@mui/material';
import { useMemo } from 'react';

const MyFormHelperText = (props: { inputText: string }) => {
  const { inputText } = props;  
    const helperText = useMemo(() => {
        return inputText;
    }, []);
  
    return <FormHelperText>{helperText}</FormHelperText>;
  }
  
export default MyFormHelperText;