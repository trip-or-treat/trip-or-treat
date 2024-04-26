import React, { useEffect, useState } from 'react';

interface Props {
  children: React.ReactNode;
}

const Deffered = ({ children }: Props) => {
  const [isDeferred, setIsDeferred] = useState(false);

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      setIsDeferred(true);
    }, 200);
    return () => clearTimeout(timeoutId);
  }, []);

  if (!isDeferred) {
    return null;
  }

  return <div>{children}</div>;
};

export default Deffered;
