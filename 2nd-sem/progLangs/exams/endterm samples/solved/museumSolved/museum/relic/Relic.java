package museum.relic;

import module java.base;

import museum.utils.RelicType;

public class Relic {
    private String relicName;
    public String getRelicName() { return relicName; }
    private RelicType relicType;
    public RelicType getRelicType() { return relicType; }
    public Relic(String relicName, RelicType relicType) {
        this.relicName = relicName;
        this.relicType = relicType;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Relic)) return false;
        Relic r = (Relic)obj;
        return relicName.equals(r.relicName) && relicType == r.relicType;
    }
    @Override
    public int hashCode() {
        return Objects.hash(relicName, relicType);
    }
    @Override
    public String toString() {
        return relicName;
    }
}