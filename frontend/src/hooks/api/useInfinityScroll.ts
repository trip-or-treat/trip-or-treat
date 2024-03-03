import { RefObject, useCallback, useEffect } from 'react';
import { useInfiniteQuery } from '@tanstack/react-query';

import { placeListFetcher } from 'src/api/placeList';

interface Props {
  observerRef: RefObject<HTMLElement>;
  regionId: string | undefined;
  keyword: string;
  prevContentTypeId: number | null;
}

const useInfinityScroll = ({ observerRef, regionId, keyword, prevContentTypeId }: Props) => {
  const { data, isLoading, hasNextPage, fetchNextPage } = useInfiniteQuery({
    queryKey: ['placeList'],
    queryFn: async ({ pageParam }) =>
      placeListFetcher({ pageParam, keyword, prevContentTypeId, regionId }),
    initialPageParam: 0,
    getNextPageParam: (_lastPage, allPages) => allPages.length + 1,
    staleTime: 0,
  });

  const handleObserver = useCallback(
    (entries: any) => {
      const [target] = entries;
      if (target.isIntersecting && hasNextPage) {
        fetchNextPage();
      }
    },
    [fetchNextPage, hasNextPage],
  );

  useEffect(() => {
    const element = observerRef.current;

    const options = {
      root: null,
      threshold: 1,
    };

    const observer = new IntersectionObserver(handleObserver, options);
    if (element) observer.observe(element);
    return () => {
      if (element) observer.unobserve(element);
    };
  }, [fetchNextPage, hasNextPage, handleObserver]);

  return { data, isLoading };
};

export default useInfinityScroll;
