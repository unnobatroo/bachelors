package ranger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamRanger {

    private List<Arrow> quiver;

    public StreamRanger() {
        this.quiver = new ArrayList<>();
    }

    public Arrow quickShot(String type) {
        return quiver.stream()
                .filter(arrow -> arrow.type.equals(type))
                .findFirst()
                .orElse(null);
    }

    public Arrow calledShot(String type) {
        return quiver.stream()
                .filter(arrow -> arrow.type.equals(type))
                .max((a1, a2) -> Integer.compare(a1.damage, a2.damage))
                .orElse(null);
    }

    public List<Arrow> rainOfArrows(String type) {
        return quiver.stream()
                .filter(arrow -> arrow.type.equals(type))
                .collect(Collectors.toList());
    }

    public int countArrows(String type) {
        return (int) quiver.stream()
                .filter(arrow -> arrow.type.equals(type))
                .count();
    }

    public boolean shoot(Arrow arrow) {
        return quiver.remove(arrow);
    }

    public boolean shootMany(Arrow[] arrows) {
        List<Arrow> arrowList = List.of(arrows);
        return quiver.removeAll(arrowList);
    }

    public void addArrow(Arrow arrow) {
        quiver.add(arrow);
    }
}
