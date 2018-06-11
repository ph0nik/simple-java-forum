package com.phonik.simpleforum.privileges;

import com.phonik.simpleforum.users.GeneralUser;

public interface BanService {

    // temporarily bans user from forum,
    // months, days and hours parameters determine length of temporal ban, after set time ban is automatically lifted
    void banUser(GeneralUser user, int months, int days, int hours);

    // permanently bans user from forum
    void banUser(GeneralUser user);

    // removes user ban
    void unbanUser(GeneralUser user);

}
