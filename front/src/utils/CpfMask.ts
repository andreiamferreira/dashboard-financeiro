const normalizeCpf = (inputCpf: string): string => {
    
    let cpfFormatted = inputCpf.replace(/\D/g, '');
    cpfFormatted = cpfFormatted.replace(/^(\d{3})(\d)/, '$1.$2');
    cpfFormatted = cpfFormatted.replace(/^(\d{3})\.(\d{3})(\d)/, '$1.$2.$3');
    cpfFormatted = cpfFormatted.replace(/^(\d{3})\.(\d{3})\.(\d{3})(\d)/, '$1.$2.$3-$4');
    return cpfFormatted;
  }

  export default normalizeCpf;