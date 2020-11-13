package me.olliem5.past.friends;

import me.olliem5.past.Past;

import java.util.ArrayList;
import java.util.List;

public class FriendsManager {
    public static List<Friend> friends = new ArrayList<>();

    public static List<Friend> getFriends() {
        return friends;
    }

    public boolean isFriend(String name) {
        boolean isFriend = false;
        for (Friend friend : getFriends()) {
            if (friend.getName().equalsIgnoreCase(name)) {
                isFriend = true;
            }
        }
        return isFriend;
    }

    public Friend getFriendByName(String name) {
        Friend fr = null;
        for (Friend friend : getFriends()) {
            if (friend.getName().equalsIgnoreCase(name)) {
                fr = friend;
            }
        }
        return fr;
    }

    public void addFriend(String name) {
        friends.add(new Friend(name));

        if (Past.configUtil != null) {
            try {
                Past.configUtil.saveFriends();
            } catch (Exception e) {}
        }
    }

    public void delFriend(String name) {
        friends.remove(getFriendByName(name));

        if (Past.configUtil != null) {
            try {
                Past.configUtil.saveFriends();
            } catch (Exception e) {}
        }
    }
}
