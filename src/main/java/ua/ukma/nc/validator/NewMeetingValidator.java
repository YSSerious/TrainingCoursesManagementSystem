package ua.ukma.nc.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Алексей on 05.12.2016.
 */
@Component
public class NewMeetingValidator {

    public boolean checkMeetingName(String meetingName){
        if("".equals(meetingName))return false;
        Pattern p = Pattern.compile("^[\\w\\s-]{2,20}$");
        Matcher m = p.matcher(meetingName);
        return m.matches();
    }

    public boolean checkMeetingPlace(String meetingPlace){
        if("".equals(meetingPlace))return false;
        Pattern p = Pattern.compile("^[\\w\\s-]{5,35}$");
        Matcher m = p.matcher(meetingPlace);
        return m.matches();
    }

    public boolean checkMeetingDate(String meetingDate){
        return "".equals(meetingDate);
    }
}
