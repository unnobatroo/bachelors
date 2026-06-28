package museum;

import module org.junit.jupiter;
import static org.junit.jupiter.api.Assertions.*;

import museum.relic.Relic;
import museum.utils.RelicType;
import museum.visitor.Visitor;
import museum.visitor.Tourist;

//javac -cp "junit6all.jar;checkthat6.jar" -p junit6all.jar --add-modules org.junit.jupiter museum/*.java museum/utils/*.java museum/visitor/*.java museum/relic/*.java structuretests-en/*.java
//javac -cp "junit6all.jar;checkthat6.jar;.;structuretests-en" -p junit6all.jar --add-modules org.junit.jupiter suites/*.java
//java -jar junit6all.jar execute -c MuseumTestSuite -cp ".;suites;structuretests-en;checkthat6.jar"

public class MuseumTest {
    @Test
    /*
          The testing should be as follows:
          1. Create an instance of a `Museum`, then create an instance of a `Tourist` with parameters of your choice.
          2. Check whether the museum's income is 0.
          3. Add your created tourist to the museum.
          4. Check the museum's income again.
    */
    public void museumIncomeTest() {
        Museum museum = new Museum();
        assertEquals(0, museum.getMuseumIncome());
        Tourist t = new Tourist("Name", RelicType.PAINTING);
        museum.addVisitingTourist(t);
        assertEquals(50, museum.getMuseumIncome());
    }
    @Test
    /*
          The testing should be as follows:
          1. Create an instance of a `Museum`, then create an instance of a `Tourist` with parameters of your choice.
          After that, create two `Relic` instances: one whose type matches the created tourist's favourite relic type
          and one whose type does not.
          2. Check whether the museum's popularity is 0.
          3. Add your created tourist to the museum.
          4. Check the museum's popularity again.
          5. Now use the appropriate method with one of the created relics.
          6. Check the museum's popularity once again.
          7. Now use the appropriate method with the other created relics.
          8. And check the museum's popularity one last time.
    */
    public void museumPopularityTest() {
        Museum museum = new Museum();
        Tourist t = new Tourist("Name", RelicType.PAINTING);
        assertEquals(0, museum.getMuseumPopularity());
        museum.addVisitingTourist(t);
        assertEquals(0, museum.getMuseumPopularity());
        Relic r = new Relic("Test", RelicType.PAINTING);
        museum.allTouristsVisitRelic(r);
        assertEquals(1, museum.getMuseumPopularity());
        Relic r2 = new Relic("Test2", RelicType.PAINTING);
        museum.allTouristsVisitRelic(r2);
        assertEquals(2, museum.getMuseumPopularity());
    }
}