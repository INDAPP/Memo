package info.socialhackathonumbria.memo.models;

import java.util.Date;

import info.socialhackathonumbria.memo.adapters.DateFormatted;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by LuckySeven srl on 04/10/2017.
 */

public class News extends RealmObject {
    public String author;
    public String title;
    public String description;
    public @PrimaryKey String url;
    public String urlToImage;
    public @DateFormatted Date publishedAt;
}
