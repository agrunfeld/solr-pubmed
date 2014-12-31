package parsers.pubmed;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public abstract class DateBase implements Date {
    public java.util.Date getConvertedDate() throws ParseException {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dateInString = String.format("%s-%s-%s", getDay().getvalue(), getMonth().getvalue(), getYear().getvalue());
        return formatter.parse(dateInString);
    }
}
