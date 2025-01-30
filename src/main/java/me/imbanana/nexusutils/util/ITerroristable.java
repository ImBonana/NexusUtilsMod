package me.imbanana.nexusutils.util;

public interface ITerroristable {
    default boolean nexusUtils$hasBombBelt() { return false; }
    default void nexusUtils$setBombBelt(boolean value) { }
    default void nexusUtils$goBoom() { }
}
