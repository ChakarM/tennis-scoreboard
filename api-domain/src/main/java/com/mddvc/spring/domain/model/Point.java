package com.mddvc.spring.domain.model;

public enum Point {
    LOVE("0"),
    FIFTEEN("15"),
    THIRTY("30"),
    FORTY("40"),
    ADVANTAGE("AD"),
    GAME("GAME");
    private final String description;

    private Point(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Point getNextScore() {
        return switch (this) {
            case LOVE -> FIFTEEN;
            case FIFTEEN -> THIRTY;
            case THIRTY -> FORTY;
            case FORTY -> ADVANTAGE;
            case ADVANTAGE, GAME -> GAME;
        };
    }
}
