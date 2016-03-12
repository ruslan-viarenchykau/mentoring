package com.ruslan.jmp.build.util;

import com.ruslan.jmp.build.bean.User;
import org.joda.time.LocalDate;
import org.joda.time.Period;

public class AgeCalculatorUtils {
    private static final String USER_AGE_MESSAGE_FORMAT = "%d years, %d month and %d days";

    public static String getUserAgeMessage(User user) {
        if (user == null || user.getBirthday() == null) {
            return null;
        }
        Period age = new Period(new LocalDate(user.getBirthday()), LocalDate.now());
        return String.format(USER_AGE_MESSAGE_FORMAT, age.getYears(), age.getMonths(), age.getDays());
    }
}
