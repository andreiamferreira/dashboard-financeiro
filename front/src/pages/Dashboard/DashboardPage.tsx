import Navbar from '../../components/Navbar/Navbar';
import Sidebar from '../../components/Sidebar/Sidebar';
import '../Style.css';
import BasicPie from '../../components/Charts/PieChart';
import SimpleLineChart from '../../components/Charts/LineChart';
import TinyBarChart from '../../components/Charts/BarChartSingle';
import './DashboardPage.css';
import PaperComponent from '../../components/Paper/PaperComponent';
import AccountUsage from '../../components/Charts/AccountUsage';

function DashboardPage() {

  return (
    <>
      <Navbar />
      <Sidebar />
      
      <div className="container">
        <div className='paper-container'>
        <PaperComponent />
        </div>
        
        <div className="chart-container">
          <div className="chart-item">
            <BasicPie />
          </div>
          <div className="chart-item">
            <AccountUsage />
          </div>
        </div>
        <div className="chart-container">
          <div className="chart-item">
            <SimpleLineChart />
          </div>
        </div>
        <div className="chart-container">
          <div className="chart-item">
            <TinyBarChart />
          </div>
        </div>
      </div>
    </>
  );
}

export default DashboardPage;

