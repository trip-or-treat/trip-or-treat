import { RefObject, useCallback, useEffect } from 'react';
import { useInfiniteQuery } from '@tanstack/react-query';

interface Props {
  observerRef: RefObject<HTMLElement>;
  queryKey: string;
  regionId: string | undefined;
  fetcherFn: any;
  keyword?: string;
  prevContentTypeId?: number | null;
}

const useInfinityScroll = ({
  observerRef,
  queryKey,
  regionId,
  keyword,
  fetcherFn,
  prevContentTypeId,
}: Props) => {
  const { data, isLoading, hasNextPage, fetchNextPage } = useInfiniteQuery({
    queryKey: [queryKey, keyword, prevContentTypeId, regionId],
    queryFn: async ({ pageParam }) =>
      fetcherFn({ pageParam, keyword, prevContentTypeId, regionId }),
    initialPageParam: 0,
    getNextPageParam: (_lastPage, allPages) => allPages.length + 1,
  });

  const handleObserver = useCallback(
    (entries: IntersectionObserverEntry[]) => {
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
