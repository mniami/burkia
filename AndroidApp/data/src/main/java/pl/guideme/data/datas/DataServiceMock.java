package pl.guideme.data.datas;

import android.content.Context;

import org.androidannotations.annotations.EBean;

import java.util.LinkedList;
import java.util.List;

import pl.guideme.data.R;
import pl.guideme.data.api.FeedResponse;
import pl.guideme.data.vo.Atomic;

@EBean(scope = EBean.Scope.Singleton)
public class DataServiceMock {

    public void mock(Context context) {
        List<FeedResponse> list = new LinkedList<>();
        FeedResponse feedResponse = createFeed(context);
        list.add(feedResponse);

        DataService_.getInstance_(context).mMemoryCache.setCachedObject(list);
    }

    private FeedResponse createFeed(Context context) {
        FeedResponse feedResponse = new FeedResponse();
        feedResponse.setAtomics(new LinkedList<>());
        String[] items = context.getResources().getStringArray(R.array.restaurants);
        for (int i = 0; i < items.length; i++) {
            feedResponse.getAtomics().add(createAtomic(context, i));
        }
        return feedResponse;
    }

    private Atomic createAtomic(Context context, int index) {
        Atomic atomic = new Atomic();
        atomic.setName(context.getResources().getStringArray(R.array.restaurants)[index]);
        atomic.setImageUrl(context.getResources().getStringArray(R.array.restaurants_image_urls)[index]);
        atomic.setTags(context.getResources().getStringArray(R.array.restaurants_tags)[index].split(","));
        return atomic;
    }
}
