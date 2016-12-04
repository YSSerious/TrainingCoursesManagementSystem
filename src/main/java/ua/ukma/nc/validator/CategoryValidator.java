package ua.ukma.nc.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Алексей on 04.12.2016.
 */
@Component
public class CategoryValidator {

    public boolean checkCategoryName(String categoryName){
        Pattern p = Pattern.compile("^[\\w\\s-]{2,20}$");
        Matcher m = p.matcher(categoryName);
        return m.matches();
    }

    public boolean checkCategoryDescription(String categoryDescription){
        Pattern p = Pattern.compile("^[\\w\\s-]{5,35}$");
        Matcher m = p.matcher(categoryDescription);
        return m.matches();
    }

    public boolean checkCriteriaName(String criteriaName){
        Pattern p = Pattern.compile("^[\\w\\s-]{2,20}$");
        Matcher m = p.matcher(criteriaName);
        return m.matches();
    }
}
