package pl.guideme.data.datas;

import org.androidannotations.annotations.EBean;

import pl.guideme.data.vo.User;

@EBean(scope = EBean.Scope.Singleton)
public class AccountService {

    public User getUser() {
        return new User();
    }
}
