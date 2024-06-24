import { PieChart } from '@mui/x-charts/PieChart';
import { useEffect, useState } from 'react';
import { getCategoriesUsed } from '../../services/Dashboard/DashboardService';
import { formatEnumValue } from '../../utils/FormatEnums';

type Category = {
  [key: string]: number;
};

interface PieChartDataItem {
  id: number;
  value: number;
  label: string;
}

export default function BasicPie() {

  const [categories, setCategories] = useState<Category>({});

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const categoriesData = await getCategoriesUsed();
        setCategories(categoriesData);
      } catch (error) {
        console.error("Error:", error);
      }
    };

    fetchCategories();
  }, []);

  const categoriesArray: PieChartDataItem[] = Object.entries(categories).map(([label, value], index) => ({
    id: index,
    value,
    label: formatEnumValue(label),
  }));
  return (
    <div>
      <h2>Categorias mais utilizadas</h2>
      <h4 style={{ color: "grey"}}>Visão Geral</h4>
      <PieChart
        series={[
          {
            data: categoriesArray,
          },
        ]}
        width={400}
        height={200}
      />
    </div>
  );
}