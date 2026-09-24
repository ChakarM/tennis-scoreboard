package com.mddvc.spring.domain.model;

import java.util.Objects;

public class PlayerModel {
    private Long id;
    private final String name;

    public PlayerModel(String name) {
        this.name = name;
    }

    public PlayerModel(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PlayerModel playerModel = (PlayerModel) o;
        return Objects.equals(name, playerModel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
