import { HttpResponse, http } from 'msw';
import { placeList } from '../data/placeList';

export const placeListHandler = [
  http.get('/places', async ({ request }) => {
    const url = new URL(request.url);

    // const productId = url.searchParams.get('regionId');
    const pageNo = url.searchParams.get('pageNo');
    // const keyword = url.searchParams.get('keyword');
    const contentId = url.searchParams.get('contentId');
    if (contentId === '12') return HttpResponse.json(placeList.slice(8, 10));
    if (contentId === '14') return HttpResponse.json(placeList.slice(12, 15));
    if (pageNo === '0') return HttpResponse.json(placeList.slice(0, 10));
    return HttpResponse.json(placeList.slice(10), { status: 200 });
  }),
];
