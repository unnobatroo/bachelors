package spaceexploration.operation;

//javac -cp ".;junit5all.jar;checkthat.jar" *.java ./spaceexploration/contract/*.java ./spaceexploration/entity/*.java ./spaceexploration/exception/*.java ./spaceexploration/model/*.java spaceexploration/operation/*.java
//java -jar junit5all.jar execute -c SpaceMissionTestSuite -cp . -cp checkthat.jar

//./check.cmd SpaceMissionTestSuite.java SpaceMissionTestSuite
//cmd /C "del /S /Q *.class"

//Grading key: Resource (15)+Ore (3)+Debris (3)+ResourceTest (4) + Astronaut (8)+Recycler (5)+InvalidResourceException (1) + SpaceMission (12) + SpaceMissionTest (7)
//15+3+3+4 + 8+5+1 + 12+7 = 58

import java.util.Set;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import spaceexploration.exception.InvalidResourceException;
import spaceexploration.model.Resource;
import spaceexploration.model.Type;
import spaceexploration.model.Ore;
import spaceexploration.model.Debris;

public class SpaceMissionTest {
    @Test
    public void testPrepareMission() throws InvalidResourceException {
        try {
            new SpaceMission(6).prepareMission(0);
            fail();
        } catch (InvalidResourceException e) {}
        new SpaceMission(6).prepareMission(4);
    }
    @Test
    public void testConductMission() throws InvalidResourceException {
        SpaceMission sm = new SpaceMission(6);
        sm.prepareMission(4);
        Set<Resource> resources = sm.conductMission();
        Ore o = new Ore("200-ME", Type.METAL);
        Debris d = new Debris("203-ME", Type.METAL);
        o.tag(); d.tag();
        assertEquals(new HashSet<>(Set.of(o, d)), resources);
    }
}
