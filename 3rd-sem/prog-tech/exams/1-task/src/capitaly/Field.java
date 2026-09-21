package capitaly;

public abstract class Field {
    private final int id;

    public Field(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract void apply(Player player);
}
