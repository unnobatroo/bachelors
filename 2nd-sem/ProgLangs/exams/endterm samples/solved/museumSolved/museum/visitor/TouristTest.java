package museum.visitor;

import module org.junit.jupiter;
import static org.junit.jupiter.api.Assertions.*;

import museum.utils.RelicType;
import museum.relic.Relic;
import museum.utils.VisitingException;

public class TouristTest {
    @Test
    public void visitRelicTest() {
        Tourist t = new Tourist("Name", RelicType.PAINTING);
        Relic r = new Relic("Test", RelicType.PAINTING);
        t.visitRelic(r);
        assertEquals(1, t.getVisitedRelicsCount());
    }
    @Test
    public void visitRelicInvalidRelicTypeTest() {
        Tourist t = new Tourist("Name", RelicType.PAINTING);
        Relic r = new Relic("Test", RelicType.SCULPTURE);
        try {
            t.visitRelic(r);
            fail();
        } catch (VisitingException e) {}
    }
    @Test
    public void visitRelicDuplicateVisitTest() {
        Tourist t = new Tourist("Name", RelicType.PAINTING);
        Relic r = new Relic("Test", RelicType.PAINTING);
        t.visitRelic(r);
        try {
            t.visitRelic(r);
            fail();
        } catch (VisitingException e) {}
    }

    @Test
    public void textualRepresentationTest() {
        Tourist t = new Tourist("Name", RelicType.PAINTING);
        assertEquals("Tourist Name hasn't visited any relics yet.", t.toString());
        Relic r = new Relic("Test", RelicType.PAINTING);
        t.visitRelic(r);
        assertEquals("Tourist Name visited the following relic(s): Test", t.toString());
        Relic r2 = new Relic("Test2", RelicType.PAINTING);
        t.visitRelic(r2);
        assertEquals("Tourist Name visited the following relic(s): Test, Test2", t.toString());
    }
}