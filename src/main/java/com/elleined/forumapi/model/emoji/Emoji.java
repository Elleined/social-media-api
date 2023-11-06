package com.elleined.forumapi.model.emoji;

public class Emoji {

    private Type type;

    public enum Type {
        LIKE,
        HEART,
        CARE,
        HAHA,
        WOW,
        SAD,
        ANGRY
    }
}
