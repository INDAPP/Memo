package info.socialhackathonumbria.memo.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by LuckySeven srl on 02/10/2017.
 */

public class Note extends RealmObject {
    public String text;
    @PrimaryKey public long time;

    public Note(String text) {
        this.text = text;
        this.time = System.currentTimeMillis();
    }

    public Note() {

    }

}
