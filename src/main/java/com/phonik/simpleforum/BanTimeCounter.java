package com.phonik.simpleforum;

import com.phonik.simpleforum.privileges.UserPrivileges;
import com.phonik.simpleforum.users.GeneralUser;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class BanTimeCounter {

    private int years;
    private int months;
    private int days;
    private int hours;
    private int minutes;
    private int seconds;
    private GeneralUser user;

    public BanTimeCounter(GeneralUser user) {
        this.user = user;
    }

    // TODO work on counter
    public void calculate() {
        LocalDateTime banLiftDate = user.getBanLiftDate();
        long liftTimeLong = banLiftDate.toEpochSecond(ZoneOffset.UTC);
        long currentTimeLong = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        LocalDateTime.ofEpochSecond(liftTimeLong - currentTimeLong, 0, ZoneOffset.UTC);
        years = banLiftDate.getYear() - LocalDateTime.now().getYear();
        months = banLiftDate.getMonthValue() - LocalDateTime.now().getMonthValue();
        days = banLiftDate.getDayOfMonth() - LocalDateTime.now().getDayOfMonth();
        hours = banLiftDate.getHour() - LocalDateTime.now().getHour();
        minutes = banLiftDate.getMinute() - LocalDateTime.now().getMinute();
        seconds = banLiftDate.getSecond() - LocalDateTime.now().getSecond();
    }

    public int getYears() {
        calculate();
        return years;
    }

    public int getMonths(){
        calculate();
        return months;
    }

    public int getDays(){
        calculate();
        return days;
    }

    public int getHours(){
        calculate();
        return hours;
    }

    public int getMinutes(){
        calculate();
        return minutes;
    }

    public int getSeconds() {
        calculate();
        return seconds;
    }

    public void resetCounter() {
        years = 0;
        months = 0;
        days = 0;
        hours = 0;
        minutes = 0;
        seconds = 0;
    }
}
