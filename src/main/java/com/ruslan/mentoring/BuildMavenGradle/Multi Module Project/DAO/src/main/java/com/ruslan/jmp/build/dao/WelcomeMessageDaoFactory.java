package com.ruslan.jmp.build.dao;

public class WelcomeMessageDaoFactory {
    public static IWelcomeMessageDao getWelcomeMessageDao() {
        return new HardcodedWelcomeMessageDao();
    }
}
