package museum.visitor;

import module java.base;

import museum.utils.VisitingException;
import museum.relic.Relic;
import museum.utils.RelicType;

public class Tourist implements Visitor {
    private String touristName;
    private RelicType favouriteRelicType;
    public RelicType getFavouriteRelicType() { return favouriteRelicType; }
    private List<Relic> visitedRelics;
    public Tourist(String touristName, RelicType favouriteRelicType) {
        this.touristName = touristName;
        this.favouriteRelicType = favouriteRelicType;
        visitedRelics = new ArrayList<>();
    }
    public int getVisitedRelicsCount() { 
        return visitedRelics.size();
    }
    @Override
    public void visitRelic(Relic relic) throws VisitingException {
        if (relic.getRelicType() != favouriteRelicType ||
            visitedRelics.contains(relic))
            throw new VisitingException();
        visitedRelics.add(relic);
    }
    @Override
    public String toString() {
        if (visitedRelics.size() == 0) {
            return "Tourist %s hasn't visited any relics yet.".formatted(touristName);
        } else if (visitedRelics.size() == 1) {
            return "Tourist %s visited the following relic(s): %s"
                .formatted(touristName, visitedRelics.get(0));
        } else {
            String s = visitedRelics.toString();
            return "Tourist %s visited the following relic(s): %s"
                .formatted(touristName, s.substring(1, s.length()-1));
        }

    }
}