package pl.guideme.data.datas;

import pl.guideme.data.bus.EventAction;
import retrofit.RetrofitError;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class DataLoadingFailureAction implements EventAction {
    private Class mDataType;
    private RetrofitError mError;

    public DataLoadingFailureAction(Class dataType, RetrofitError error) {
        mDataType = dataType;
        mError = error;
    }

    public RetrofitError getError() {
        return mError;
    }

    @Override
    public Object getKey() {
        return getClass();
    }

    public Class getDataType() {
        return mDataType;
    }
}
