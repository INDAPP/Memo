package info.socialhackathonumbria.memo.models;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by LuckySeven srl on 09/10/2017.
 */

public class Source extends RealmObject{
    public @PrimaryKey String id;
    public String name;
    public String description;
    public String url;
    public String category;
    public String language;
    public String country;

}
