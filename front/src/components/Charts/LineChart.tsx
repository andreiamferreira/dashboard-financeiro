import { LineChart } from '@mui/x-charts/LineChart';
import { useEffect, useState } from 'react';
import { getTransactionsMonthlySummary } from '../../services/Dashboard/DashboardService';


interface TransactionData {
  [month: string]: {
    in: number;
    out: number;
  };
}

interface ChartData {
  in: number[];
  out: number[];
  labels: string[];
}

function generateMonthLabels(data: TransactionData) {
  const now = new Date();
  const year = now.getFullYear();
  const labels = [];

  for (let month = 0; month < now.getMonth() + 1; month++) {
    const monthLabel = `${year}-${String(month + 1).padStart(2, '0')}`;
    if (data[monthLabel]) {
      labels.push(monthLabel);
    }
  }

  return labels;
}


export default function SimpleLineChart() {
  const [data, setData] = useState<ChartData>({ in: [], out: [], labels: [] });

  useEffect(() => {
    const fetchTransactions = async () => {
      try {
        const transactionData: TransactionData = await getTransactionsMonthlySummary();

        const labels = generateMonthLabels(transactionData);
        const inData: number[] = labels.map(month => transactionData[month]?.in || 0);
        const outData: number[] = labels.map(month => transactionData[month]?.out || 0);

        const xLabels: string[] = [];

        console.log(inData)
        labels.forEach(month => {
          if (transactionData[month]) {
            inData.push(transactionData[month].in || 0);
            outData.push(transactionData[month].out || 0);
          } else {
            inData.push(0);
            outData.push(0);
          }
        });

        Object.entries(transactionData).forEach(([month, types]) => {
          xLabels.push(month);
          inData.push(types.in || 0);
          outData.push(types.out || 0);
        });

        setData({ in: inData, out: outData, labels: xLabels });

      } catch (error) {
        console.error("Error:", error);
      }
    };

    fetchTransactions();
  }, []);

  return (
    <div>
      <h2>Relação entrada x saída</h2>
      <LineChart
        width={1000}
        height={400}
        series={[
          { data: data.in, label: 'Entrada', color: 'green' },
          { data: data.out, label: 'Saída', color: 'red' },
        ]}
        xAxis={[{ scaleType: 'point', data: data.labels }]}
      />
    </div>
  );
}
