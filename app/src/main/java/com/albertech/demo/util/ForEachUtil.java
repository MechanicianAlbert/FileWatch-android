package com.albertech.demo.util;

import java.util.List;

public class ForEachUtil {

    public interface ItemHandler<Item> {
        void handle(Item item);
    }

    public static void forEach(List list, ItemHandler ih) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                ih.handle(list.get(i));
            }
        }
    }
}
