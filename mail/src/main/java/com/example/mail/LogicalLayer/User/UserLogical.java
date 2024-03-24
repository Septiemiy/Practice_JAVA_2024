package com.example.mail.LogicalLayer.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public class UserLogical {
    
    public static Boolean isNameNotNullNotEmpty(String username) {
        if(username == null || username.isEmpty()) {
                return true;
            }
        return false;
    }

    public static Boolean isEmailNotNullNotEmpty(String email) {
        if(email == null || email.isEmpty()) {
                return true;
            }
        return false;
    }

    public static Boolean isValidEmail(String email) {
        String regex = "^(?!.*[._%+-]{2})[a-zA-Z0-9._%+-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z]{2,7})+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Pageable getPageable(int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber, 3, Sort.by("createdOn").descending());
        return pageable;
    }
}
