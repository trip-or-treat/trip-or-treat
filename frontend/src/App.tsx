import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import routes from './routes';

function App() {
  const router = createBrowserRouter(routes);

  return <RouterProvider router={router} />;
}

export default App;
