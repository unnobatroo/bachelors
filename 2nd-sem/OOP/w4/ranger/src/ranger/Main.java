package ranger;

public class Main {

    public static void main(String[] args) {
        // Create some arrows
        Arrow fireArrow1 = new Arrow(10, "Fire");
        Arrow fireArrow2 = new Arrow(15, "Fire");
        Arrow fireArrow3 = new Arrow(12, "Fire");
        Arrow iceArrow1 = new Arrow(12, "Ice");
        Arrow iceArrow2 = new Arrow(8, "Ice");
        Arrow poisonArrow1 = new Arrow(20, "Poison");
        Arrow poisonArrow2 = new Arrow(20, "Poison");
        Arrow poisonArrow3 = new Arrow(25, "Poison");
        Arrow poisonArrow4 = new Arrow(27, "Poison");

        Ranger ranger = new Ranger();
        ranger.addArrow(fireArrow1);
        ranger.addArrow(iceArrow1);
        ranger.addArrow(poisonArrow1);
        ranger.addArrow(poisonArrow3);
        ranger.addArrow(fireArrow3);

        StreamRanger streamRanger = new StreamRanger();
        streamRanger.addArrow(fireArrow2);
        streamRanger.addArrow(iceArrow2);
        streamRanger.addArrow(poisonArrow2);
        streamRanger.addArrow(poisonArrow4);

        System.out.println(
                "Ranger Quick Shot (Fire): " + ranger.quickShot("Fire").damage + " " + ranger.quickShot("Fire").type);
        System.out.println("StreamRanger Quick Shot (Fire): " + streamRanger.quickShot("Fire").damage + " "
                + streamRanger.quickShot("Fire").type);
        System.out.println("Ranger arrow count (fire) " + ranger.countArrows("Fire"));
        System.out.println("StreamRanger arrow count (fire) " + streamRanger.countArrows("Fire"));

        System.out.println("Ranger CalledShot (Poison): " + ranger.calledShot("Poison").damage + " "
                + ranger.calledShot("Poison").type);
        System.out.println("StreamRanger CalledShot (Poison): " + streamRanger.calledShot("Poison").damage + " "
                + streamRanger.quickShot("Poison").type);

        System.out.println("Ranger Shooting Poisoned arrow " + ranger.shoot(poisonArrow1));
        System.out.println("Stream Ranger Shooting Poisoned arrow : " + streamRanger.shoot(poisonArrow2));
        System.out.println("Ranger arrow count (Posion) " + ranger.countArrows("Poison"));
        System.out.println("StreamRanger arrow count (Poison) " + streamRanger.countArrows("Poison"));

        Arrow[] arrowsToShoot = { fireArrow1, iceArrow1 };

        System.out.println("Ranger shooting multiple arrows: " + ranger.shootMany(arrowsToShoot));
        System.out.println("Ranger arrow count (Fire): " + ranger.countArrows("Fire"));
        System.out.println("Ranger arrow count (Ice): " + ranger.countArrows("Ice"));

        Arrow[] streamArrowsToShoot = { fireArrow2, iceArrow2 };

        System.out.println("StreamRanger shooting multiple arrows: " + streamRanger.shootMany(streamArrowsToShoot));
        System.out.println("StreamRanger arrow count (Fire): " + streamRanger.countArrows("Fire"));
        System.out.println("StreamRanger arrow count (Ice): " + streamRanger.countArrows("Ice"));

    }
}
