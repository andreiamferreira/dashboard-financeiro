export const isIn = (category: string): boolean => {
    const inCategories = [
      "SALARIO", // in
      "EMPRESTIMO", // in
    ];
  
    return inCategories.includes(category);
  };
