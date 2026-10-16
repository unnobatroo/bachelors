package capitaly;

public abstract class Field {
    private final int id;

    public Field(int id) {
        if (id < 0) {
            throw new IllegalArgumentException(
                "Field id cannot be negative, got: " + id
            );
        }
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract void apply(Player player);
}
