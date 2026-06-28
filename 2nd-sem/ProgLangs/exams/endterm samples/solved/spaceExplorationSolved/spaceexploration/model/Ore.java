package spaceexploration.model;

public class Ore extends Resource {
    public Ore(String position, Type type) {
        super(position, type);
        isStable = false;
    }
    @Override
    public void extendPosition(String extension)
    {
        position += extension;
    }
    @Override
    public String collect() {
        if (!isStable && !tagged) return null;
        int l = position.length() + type.name().length();
        extendPosition(l >= 8 && l <= 12 ? "#FOUND" : "#REJECT");
        return position;
    }
    @Override
    public void tag() {
        setStable(!isStable);
        tagged = true;
    }
}
