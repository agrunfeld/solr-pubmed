package medline;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public abstract class DateBase implements Date {
    public java.util.Date getConvertedDate() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dateInString = String.format("%s-%s-%s", getDay().getvalue(), getMonth().getvalue(), getYear().getvalue());
        return formatter.parse(dateInString);
    }
}
