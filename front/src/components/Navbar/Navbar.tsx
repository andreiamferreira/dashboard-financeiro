import { VscAccount } from "react-icons/vsc";


import "./Navbar.css";
interface NavBarProps {}

const Navbar: React.FC<NavBarProps> = () => {


    return (
    <header>
        <nav className="icon">
                <VscAccount />
        </nav>
        
    </header>
    )
}

export default Navbar;