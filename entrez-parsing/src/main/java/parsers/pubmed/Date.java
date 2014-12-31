package parsers.pubmed;

import java.text.ParseException;

public interface Date {
    public Year getYear();
    public Month getMonth();
    public Day getDay();
    public void setYear(Year year);
    public void setMonth(Month month);
    public void setDay(Day day);
    public java.util.Date getConvertedDate() throws ParseException;
}