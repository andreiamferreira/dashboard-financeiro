import AccountBalanceWalletIcon from '@mui/icons-material/AccountBalanceWallet';

import SavingsIcon from '@mui/icons-material/Savings';
import TimelineIcon from '@mui/icons-material/Timeline';
import PaidIcon from '@mui/icons-material/Paid';


export const SidebarData = [
    {
        title: "Dashboard Geral",
        icon: <TimelineIcon />,
        link: "/home"
    },
    {
        title: "Contas",
        icon: <AccountBalanceWalletIcon />,
        link: "/accounts"
    },
    {
        title: "Transações",
        icon: <PaidIcon/>,
        link: "/transactions"
    },
    {
        title: "Metas",
        icon: <SavingsIcon />,
        link: "/goals"
    }
]