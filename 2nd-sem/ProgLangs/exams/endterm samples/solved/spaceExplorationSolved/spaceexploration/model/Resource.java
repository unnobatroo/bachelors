package spaceexploration.model;

import java.util.Objects;

import spaceexploration.contract.Tagged;

public abstract class Resource implements Tagged {
    protected String position;
    protected Type type;
    protected boolean isStable;
    protected boolean tagged;
    public Resource(String position, Type type) {
        if (position == null || position.length() < 4) throw new IllegalArgumentException();
        this.position = position;
        this.type = type;
        isStable = true;
    }
    public abstract void extendPosition(String extension);
    public abstract String collect();
    @Override
    public String toString() { return "Position: " + position + ", Type: " + type + ", isStable: " + isStable; }
    @Override
    public int hashCode() { return Objects.hash(isStable, position.substring(0, 4), type); }
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Resource)) return false;
        Resource r = (Resource)o;
        return isStable == r.isStable && position.substring(0, 4).equals(r.position.substring(0, 4)) && type == r.type;
    }
    public String getPosition() { return position; }
    public Type getType() { return type; }
    public boolean isStable() { return isStable; }
    public void setStable(boolean stable) { isStable = stable; }
    public boolean isTagged() { return tagged; }
    public abstract void tag();
}
