import { Typography } from '@mui/material';
import { BarChart } from '@mui/x-charts/BarChart';
import { useEffect, useState } from 'react';
import { getTransactionsTypeMonthlySummary } from '../../services/Dashboard/DashboardService';
import { formatEnumValue } from '../../utils/FormatEnums';

interface TransactionSummaryByType {
  in: number;
  out: number;
}

interface TransactionDataByMonth {
  [month: string]: {
    [type: string]: TransactionSummaryByType;
  };
}

interface ChartData {
  [type: string]: { in: number; out: number }[];
}

function generateMonthLabels() {
  const now = new Date();
  const year = now.getFullYear();
  const months = [];

  for (let month = 0; month <= now.getMonth(); month++) {
    months.push(`${year}-${String(month + 1).padStart(2, '0')}`);
  }

  return months;
}

export default function TinyBarChart() {
  const [data, setData] = useState<{ chartData: ChartData; labels: string[] }>({ chartData: {}, labels: [] });

  useEffect(() => {
    const fetchTransactions = async () => {
      try {
        const transactionData: TransactionDataByMonth = await getTransactionsTypeMonthlySummary();
        const labels = generateMonthLabels();
        const chartData: ChartData = {};

        labels.forEach(month => {
          Object.keys(transactionData[month] || {}).forEach(category => {
            if (!chartData[category]) {
              chartData[category] = Array(labels.length).fill({ in: 0, out: 0 });
            }
          });
        });

        Object.entries(transactionData).forEach(([month, categories]) => {
          const monthIndex = labels.indexOf(month);
          if (monthIndex !== -1) {
            Object.entries(categories).forEach(([category, types]) => {
              if (!chartData[category]) {
                chartData[category] = Array(labels.length).fill({ in: 0, out: 0 });
              }
              chartData[category][monthIndex] = { in: types.in || 0, out: types.out || 0 };
            });
          }
        });

        setData({ chartData, labels });
      } catch (error) {
        console.error("Error:", error);
      }
    };

    fetchTransactions();
  }, []);

  const renderLegend = (legendText: string, color: string) => (
    <div style={{ display: 'flex', alignItems: 'center', marginRight: '20px' }}>
      <div style={{ width: '10px', height: '10px', backgroundColor: color, marginRight: '5px' }}></div>
      <Typography variant="body2">{legendText}</Typography>
    </div>
  );

  const types = Object.keys(data.chartData);
  const colors = ['#FF5733', '#3366FF', '#9BE2B0'];

  return (
    <>
      <h2>Tipos de transações utilizadas</h2>
      <h4 style={{ color: 'grey' }}>Anual</h4>
      <div style={{ display: 'flex', marginBottom: '20px' }}>
        {types.map((type, index) => renderLegend(formatEnumValue(type), colors[index % colors.length]))}
      </div>
      <BarChart
        xAxis={[{ scaleType: 'band', data: data.labels }]}
        series={types.map((type, index) => ({
          data: data.chartData[type].map(entry => entry.in - entry.out),
          color: colors[index % colors.length],
          label: formatEnumValue(type),
        }))}
        width={1000}
        height={300}
      />
    </>
  );
}