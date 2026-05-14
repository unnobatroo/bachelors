package museum;

import module java.base;

import museum.visitor.Tourist;
import museum.relic.Relic;

public class Museum {
    private static int TICKET_PRICE = 50;
    private List<Tourist> visitingTourists;
    private Map<String, Integer> museumData;
    public Museum() {
        visitingTourists = new ArrayList<>();
        museumData = new HashMap<>();
        museumData.put("income", 0);
        museumData.put("popularity", 0);
    }
    private void calculateMuseumData() {
        museumData.put("income", visitingTourists.size() * TICKET_PRICE);
        int popularity = 0;
        for (Tourist t : visitingTourists) {
            popularity += t.getVisitedRelicsCount();
        }
        museumData.put("popularity", popularity);
    }
    public void addVisitingTourist(Tourist tourist) {
        visitingTourists.add(tourist);
        calculateMuseumData();
    }
    public void allTouristsVisitRelic(Relic relic) {
        for (Tourist t : visitingTourists) {
            if (t.getFavouriteRelicType() == relic.getRelicType())
                t.visitRelic(relic);
        }
        calculateMuseumData();
    }
    public int getMuseumIncome() {
        return museumData.get("income");
    }
    public int getMuseumPopularity() {
        return museumData.get("popularity");
    }
}