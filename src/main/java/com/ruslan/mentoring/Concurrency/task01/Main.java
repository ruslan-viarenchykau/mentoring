package com.ruslan.mentoring.Concurrency.task01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static List<Thread> participants;
    private static String winnerName;
    private static long startTime;
    private static volatile int done;

    public static void main(String[] args) throws InterruptedException {
        startRace();
        Thread.sleep(5000);
        disqualifyParticipant();
    }

    public static void handleFinished(String name) {
        if (winnerName == null) {
            winnerName = name;
            LOGGER.info("Winner is {}", name);
        }
        LOGGER.info("{} finished after {}", name, String.valueOf(new Date().getTime() - startTime));
        handleDone();
    }

    private static void startRace() {
        createParticipants();
        for (Thread participant: participants) {
            participant.start();
        }
        startTime = new Date().getTime();
    }

    private static void disqualifyParticipant() {
        Thread car = participants.get(4);
        car.interrupt();
    }

    public static void handleDisqualified(String name) {
        LOGGER.info("{} was disqualified", name);
        handleDone();
    }

    private static void handleDone() {
        if (++done == participants.size()) {
            LOGGER.info("Race is over");
        }
    }

    private static void createParticipants () {
        participants = new ArrayList<Thread>(){{
            add(new Thread(new Car("car1", 20)));
            add(new Thread(new Car("car2", 50)));
            add(new Thread(new Car("car3", 70)));
            add(new Thread(new Car("car4", 75)));
            add(new Thread(new Car("car5", 1000)));
        }};
    }
}
