package museum.visitor;

import museum.relic.Relic;
import museum.utils.VisitingException;

public interface Visitor {
    public void visitRelic(Relic relic) throws VisitingException;
}