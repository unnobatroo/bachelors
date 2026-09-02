package spaceexploration.model;

public class Debris extends Resource {
    public Debris(String position, Type type) {
        super(position, type);
    }
    @Override
    public void extendPosition(String extension)
    {
        position += extension;
    }
    @Override
    public String collect() {
        if (!tagged) return null;
        extendPosition("#DISCARD");
        return position;
    }
    @Override
    public void tag() {
        tagged = true;
    }
    @Override
    public void setStable(boolean stable) {}
}
