package com.ruslan.jmp.build.dao;

public class HardcodedWelcomeMessageDao implements IWelcomeMessageDao {
    public String getWelcomeMessage() {
        return "Welcome! Please fill out the simple form below";
    }
}
