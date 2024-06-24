import React, { useState } from 'react';
import './Sidebar.css';
import { SidebarData } from './SidebarData';
import { ExitToApp } from '@mui/icons-material';

interface SidebarProps{}

const Sidebar: React.FC<SidebarProps> = () => {

    const [activeLink, setActiveLink] = useState<string>(window.location.pathname);

    const handleLinkClick = (link: string) => {
        setActiveLink(link);
        window.location.pathname = link;
    };

    const handleLogout = () => {
        localStorage.removeItem("token");
        window.location.pathname = '/login';
      };

    return (
        <div className='sidebar-body'>
            <ul className='list'>
            {SidebarData.map((value, key) => {
                return (
                    <li key={key} className='row' id={activeLink === value.link ? "active" : ""} onClick={() => handleLinkClick(value.link)}>
                    {""}
                    <div id="icon">{value.icon}</div>
                    <div id="title">
                        {value.title} 
                    </div>
                </li>
                );
            })}
               <li className='row' id="logout" onClick={handleLogout}>
                <div id="icon"><ExitToApp /></div>
                <div id="title">Sair</div>
               </li>
            </ul>
        </div>
    );            
}

export default Sidebar;
