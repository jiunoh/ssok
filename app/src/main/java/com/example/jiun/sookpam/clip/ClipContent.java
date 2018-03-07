package com.example.jiun.sookpam.clip;

import android.widget.ImageView;

import com.example.jiun.sookpam.R;
import com.example.jiun.sookpam.model.vo.ClipVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ClipContent {
    public static final List<ClipItem> ITEMS = new ArrayList<ClipItem>();
    public static final Map<String, ClipItem> ITEM_MAP = new HashMap<String, ClipItem>();
    private static final int COUNT = 25;
    private static ClipDBManager dbManager;

    static {
        dbManager = new ClipDBManager(Realm.getDefaultInstance());
        List<ClipVO> response = dbManager.select();
        for (ClipVO record :  response )
            addItem(record);
    }

    private static void addItem(ClipVO record) {
        ClipItem item = new ClipItem(record.getTitle(), record.getCategory());
        ITEMS.add(item);
        ITEM_MAP.put(item.title, item);
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
    public static class ClipItem {
        public final String title;
        public final String category;
        public ImageView star;

        public ClipItem(String title, String category) {
            this.title = title;
            this.category = category;
            this.star.setImageResource(R.drawable.star_on);
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
