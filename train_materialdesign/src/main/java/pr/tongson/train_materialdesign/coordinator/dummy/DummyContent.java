package pr.tongson.train_materialdesign.coordinator.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pr.tongson.train_materialdesign.coordinator.google.ScrollingActivity;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Integer, DummyItem> ITEM_MAP = new HashMap<Integer, DummyItem>();


    static {
        DummyItem dummyItem = DummyItem.onCreate(ScrollingActivity.class);
        ITEMS.add(dummyItem);
        ITEM_MAP.put(dummyItem.getClz().getClass().hashCode(), dummyItem);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem<T> {

        private T clz;

        public static <T> DummyItem<T> onCreate(T t) {
            return new DummyItem<>(t);
        }

        public DummyItem(T clz) {
            this.clz = clz;
        }


        public T getClz() {
            return clz;
        }

        @Override
        public String toString() {
            return String.valueOf(clz.getClass().hashCode());
        }
    }
}
