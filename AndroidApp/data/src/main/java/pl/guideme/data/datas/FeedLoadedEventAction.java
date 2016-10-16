package pl.guideme.data.datas;

import pl.guideme.data.api.FeedResponse;
import pl.guideme.data.bus.EventAction;

public class FeedLoadedEventAction implements EventAction {
    private FeedResponse mItem;

    public FeedLoadedEventAction(FeedResponse item) {
        mItem = item;
    }

    @Override
    public Object getKey() {
        return getClass();
    }

    public FeedResponse getItem() {
        return mItem;
    }
}
