package info.socialhackathonumbria.memo;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by LuckySeven srl on 02/10/2017.
 */

public class Memo extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }

}
