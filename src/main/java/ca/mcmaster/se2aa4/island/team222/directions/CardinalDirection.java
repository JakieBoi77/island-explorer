package ca.mcmaster.se2aa4.island.team222.directions;

public enum CardinalDirection {
    
    N, E, S, W;

    public CardinalDirection nextRight() {
        CardinalDirection newDirection;
        switch (this) {
            case N:
                newDirection = E;
                break;
            case S:
                newDirection = W;
                break;
            case W:
                newDirection = N;
                break;
            case E:
                newDirection = S;
                break;
            default:
                throw new IllegalStateException(String.format("Unexpected value: %s", this));

        }
        return newDirection;
    }

    public CardinalDirection nextLeft() {
        CardinalDirection newDirection;
        switch (this) {
            case N:
                newDirection = W;
                break;
            case S:
                newDirection = E;
                break;
            case W:
                newDirection = S;
                break;
            case E:
                newDirection = N;
                break;
            default:
                throw new IllegalStateException(String.format("Unexpected value: %s", this));

        }
        return newDirection;
    }
}
