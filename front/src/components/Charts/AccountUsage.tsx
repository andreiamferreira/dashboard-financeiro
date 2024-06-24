import React, { useEffect, useState } from 'react';
import { getTransactionTypes } from '../../services/Dashboard/DashboardService';
import { formatEnumValue } from '../../utils/FormatEnums';

interface AccountUsageProps {}

type Type = {
  [key: string]: number;
};

interface AccountUsageDataItem {
  id: number;
  value: number;
  label: string;
}

const AccountUsage: React.FC<AccountUsageProps> = () => {

  const [types, setTypes] = useState<Type>({});

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const transactionData = await getTransactionTypes();
        setTypes(transactionData);
      } catch (error) {
        console.error("Error:", error);
      }
    };

    fetchCategories();
  }, []);

  const typeArray: AccountUsageDataItem[] = Object.entries(types).map(([label, value], index) => ({
    id: index,
    value,
    label: formatEnumValue(label),
  }));

  const renderUnderline = (size: number): JSX.Element => {
    const style: React.CSSProperties = {
      width: `${size * 5}%`,
      height: '2px',
      backgroundColor: size >= 3 ? 'green' : 'red',
    };
    return <div style={style}></div>;
  };

  return (
    <div>
      <h2>Contas</h2>
      <h4 style={{color: 'grey'}}>Seus tipos de contas mais utilizados</h4>
      {typeArray.map(({ id, label, value }) => (
        <div key={id}>
          <h3>{label}</h3>
          <p>{value}</p>
          {renderUnderline(value)}
        </div>
      ))}
    </div>
  );
};

export default AccountUsage;
