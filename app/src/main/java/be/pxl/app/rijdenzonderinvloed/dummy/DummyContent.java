package be.pxl.app.rijdenzonderinvloed.dummy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.pxl.app.rijdenzonderinvloed.data.Consumptie;
import be.pxl.app.rijdenzonderinvloed.data.OnderInvloed;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    private ArrayList<OnderInvloed> ONDER_INVLOED_ARRAY_LIST = new ArrayList<>();

    public DummyContent() {
        OnderInvloed oi_1 = new OnderInvloed();
        oi_1.setId("a");
        oi_1.setGeslacht("man");
        oi_1.setGewicht(75);
        oi_1.setDatumDrinkAvond(LocalDate.parse("2020-01-22"));
        oi_1.setTijdEersteConsumptie(LocalTime.parse("10:15:30"));
        oi_1.setTijdEersteConsumptie(LocalTime.parse("15:15:30"));
        oi_1.setMaxPromille(4.2);
        oi_1.voegConsumptieToe(new Consumptie(1, LocalDate.now(), LocalTime.now()));
        oi_1.voegConsumptieToe(new Consumptie(3, LocalDate.now(), LocalTime.now()));
        oi_1.voegConsumptieToe(new Consumptie(2, LocalDate.now(), LocalTime.now()));
        oi_1.voegConsumptieToe(new Consumptie(1, LocalDate.now(), LocalTime.now()));
        ONDER_INVLOED_ARRAY_LIST.add(oi_1);

        OnderInvloed oi_2 = new OnderInvloed();
        oi_2.setId("b");
        oi_2.setGeslacht("man");
        oi_2.setGewicht(85);
        oi_2.setDatumDrinkAvond(LocalDate.parse("2020-01-23"));
        oi_2.setTijdEersteConsumptie(LocalTime.parse("11:15:30"));
        oi_2.setTijdEersteConsumptie(LocalTime.parse("20:15:30"));
        oi_2.setMaxPromille(0.3);
        oi_2.voegConsumptieToe(new Consumptie(2, LocalDate.now(), LocalTime.now()));
        oi_2.voegConsumptieToe(new Consumptie(3, LocalDate.now(), LocalTime.now()));
        oi_2.voegConsumptieToe(new Consumptie(2, LocalDate.now(), LocalTime.now()));
        oi_2.voegConsumptieToe(new Consumptie(4, LocalDate.now(), LocalTime.now()));
        ONDER_INVLOED_ARRAY_LIST.add(oi_2);

        OnderInvloed oi_3 = new OnderInvloed();
        oi_3.setId("c");
        oi_3.setGeslacht("man");
        oi_3.setGewicht(75);
        oi_3.setDatumDrinkAvond(LocalDate.parse("2020-01-24"));
        oi_3.setTijdEersteConsumptie(LocalTime.parse("10:15:30"));
        oi_3.setTijdEersteConsumptie(LocalTime.parse("15:15:30"));
        oi_3.setMaxPromille(0.7);
        oi_3.voegConsumptieToe(new Consumptie(1, LocalDate.now(), LocalTime.now()));
        oi_3.voegConsumptieToe(new Consumptie(3, LocalDate.now(), LocalTime.now()));
        oi_3.voegConsumptieToe(new Consumptie(2, LocalDate.now(), LocalTime.now()));
        oi_3.voegConsumptieToe(new Consumptie(1, LocalDate.now(), LocalTime.now()));
        ONDER_INVLOED_ARRAY_LIST.add(oi_3);

        OnderInvloed oi_4 = new OnderInvloed();
        oi_4.setId("c");
        oi_4.setGeslacht("man");
        oi_4.setGewicht(75);
        oi_4.setDatumDrinkAvond(LocalDate.parse("2020-01-25"));
        oi_4.setTijdEersteConsumptie(LocalTime.parse("10:15:30"));
        oi_4.setTijdEersteConsumptie(LocalTime.parse("15:15:30"));
        oi_4.setMaxPromille(2.7);
        oi_4.voegConsumptieToe(new Consumptie(1, LocalDate.now(), LocalTime.now()));
        oi_4.voegConsumptieToe(new Consumptie(3, LocalDate.now(), LocalTime.now()));
        oi_4.voegConsumptieToe(new Consumptie(2, LocalDate.now(), LocalTime.now()));
        oi_4.voegConsumptieToe(new Consumptie(1, LocalDate.now(), LocalTime.now()));
        ONDER_INVLOED_ARRAY_LIST.add(oi_4);
    }

    public ArrayList<OnderInvloed> getOnderInvloedArrayList() {
        return ONDER_INVLOED_ARRAY_LIST;
    }

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
