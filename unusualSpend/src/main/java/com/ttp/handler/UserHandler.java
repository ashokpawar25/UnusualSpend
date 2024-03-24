package com.ttp.handler;

import com.ttp.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserHandler
{
    public List<User> users = new ArrayList<>();

    public void addUser(User user)
    {
        this.users.add(user);
    }


}
