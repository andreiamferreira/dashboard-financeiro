export const formatEnumValue = (value: string): string => {
    const specialCases: { [key: string]: string } = {
      BANCO_DO_BRASIL: 'Banco do Brasil',
      CAIXA_ECONOMICA: 'Caixa Econômica',
      ITAU: 'Itaú',
      POUPANCA: 'Poupança',
      CONTA_CORRENTE: 'Conta Corrente',
      SALARIO: 'Salário',
      DEBITO: 'Débito',
      CREDITO: 'Crédito',
      PAGBANK: 'PagBank',
      FARMACIA: 'Farmácia',
      MEDICO: 'Médico',
      EMPRESTIMO: 'Empréstimo',
      EDUCACAO: 'Educação',
    };
  
    if (specialCases[value]) {
      return specialCases[value];
    }
  
    return value
      .toLowerCase()
      .replace(/_/g, ' ')
      .replace(/\b\w/g, (char) => char.toUpperCase());
  };