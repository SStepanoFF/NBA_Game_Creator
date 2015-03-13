import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sergii.stepanov on 13/03/2015.
 */
public class testing {

    private String game_time="06:37";
    @Test
    public void test(){
        String UTC_time=Integer.toString(Integer.parseInt(game_time.substring(0,2))+5)+game_time.substring(2,5);
        System.out.print(UTC_time);
    }

    public Date dataConvertfromString(String text) {
        Date date=null;
        try{
            SimpleDateFormat format=new SimpleDateFormat("HH:mm", Locale.UK);
            date=format.parse(text);
        }catch (ParseException e){}
        return date;
    }
}
