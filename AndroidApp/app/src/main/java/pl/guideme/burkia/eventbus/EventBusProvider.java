package pl.guideme.burkia.eventbus;

import de.greenrobot.event.EventBus;
import org.androidannotations.annotations.EBean;

@EBean(scope = EBean.Scope.Singleton)
public class EventBusProvider {
    private EventBus eventBus;

    public EventBusProvider(){
        eventBus = new EventBus();
    }
    public EventBus get(){
        return eventBus;
    }
}
