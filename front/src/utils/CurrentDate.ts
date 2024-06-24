export function currentMonth(): string | undefined {
    const currentDate = new Date();
    const currentMonthIndex = currentDate.getMonth();

    const monthPortuguese = [
        'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
        'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'
    ];

    if (currentMonthIndex >= 0 && currentMonthIndex < monthPortuguese.length) {
        return monthPortuguese[currentMonthIndex];
    } else {
        console.error('Erro: Índice de mês inválido!');
        return undefined;
    }
}

export function currentYear(): number {
    const currentDate = new Date();
    return currentDate.getFullYear();
}
