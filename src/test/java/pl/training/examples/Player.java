package pl.training.examples;

public enum Player {

    CIRCLE, CROSS;

    public Player reverse() {
        return this == CIRCLE ? CROSS : CIRCLE;
    }

}
