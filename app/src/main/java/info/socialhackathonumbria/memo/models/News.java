package info.socialhackathonumbria.memo.models;

import java.util.Date;

import info.socialhackathonumbria.memo.adapters.DateFormatted;

/**
 * Created by LuckySeven srl on 04/10/2017.
 */

public class News {
    public String author;
    public String title;
    public String description;
    public String url;
    public String urlToImage;
    public @DateFormatted Date publishedAt;
}
