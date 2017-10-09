package info.socialhackathonumbria.memo.adapters;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by LuckySeven srl on 09/10/2017.
 */

public class DateAdapter {
    static final SimpleDateFormat FORMATTER =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);

    @FromJson()
    public @DateFormatted Date from(String dateString) {
        try {
            Date date = FORMATTER.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @ToJson()
    public String to(@DateFormatted Date date) {
        return FORMATTER.format(date);
    }

}
