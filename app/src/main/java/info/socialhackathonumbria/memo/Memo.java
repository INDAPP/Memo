package info.socialhackathonumbria.memo;

import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;

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

    public static int getPrimaryColor(Context ctx) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = ctx.obtainStyledAttributes(typedValue.data,
                new int[] {R.attr.colorPrimary});
        int color = a.getColor(0, 0);
        a.recycle();

        return color;
    }

}
