import { useState, useEffect, ComponentType, FC } from "react";
import { Navigate } from "react-router-dom";

interface PrivateRouteProps {
  Component: ComponentType;
}

const PrivateRoute: FC<PrivateRouteProps> = ({ Component }) => {
  const [isLoading, setIsLoading] = useState(true);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      setIsAuthenticated(true);
    }
    setIsLoading(false);
    console.log(token)
  }, []);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return isAuthenticated ? <Component /> : <Navigate to="/login" />;
};

export default PrivateRoute;
