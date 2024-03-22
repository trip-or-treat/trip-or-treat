import { useEffect, useRef, useState } from 'react';

export const useCarousel = (length: number, padding: number) => {
  const [currentSlide, setCurrentSlide] = useState(0);
  const slideRef = useRef<HTMLDivElement>(null);
  const cal = currentSlide * (window.innerWidth - padding);

  const handlePrev = () => {
    if (currentSlide === 0) {
      setCurrentSlide(length);
    } else {
      setCurrentSlide(currentSlide - 1);
    }
  };

  const handleNext = () => {
    if (currentSlide >= length) {
      setCurrentSlide(0);
    } else {
      setCurrentSlide(currentSlide + 1);
    }
  };

  useEffect(() => {
    if (slideRef.current) {
      slideRef.current.style.transform = `translateX(-${cal}px)`;
    }
  }, [currentSlide]);

  return { handlePrev, handleNext, slideRef, currentSlide };
};
