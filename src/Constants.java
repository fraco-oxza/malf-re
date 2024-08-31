public enum Constants {
    EPSILON,
    PHI;

    public String toString() {
        return switch (this) {
            case EPSILON -> "ε";
            case PHI -> "Φ";
        };
    }
}
