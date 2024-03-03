package me.imbanana.nexusutils.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum SleepingBagPart implements StringIdentifiable {
    HEAD("head"),
    FOOT("foot");

    private final String name;

    SleepingBagPart(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
