package com.albertech.demo.util;

import java.util.List;
import java.util.Map;
import java.util.Set;



public class ForEachUtil {

    public interface ItemHandler<Item> {
        void handle(Item item);
    }


    public static <Item> void forEach(Item[] arr, ItemHandler<Item> ih) {
        if (arr != null && arr.length > 0) {
            for (int i = 0; i < arr.length; i++) {
                ih.handle(arr[i]);
            }
        }
    }

    public static <Item> void forEach(List<Item> list, ItemHandler<Item> ih) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                ih.handle(list.get(i));
            }
        }
    }

    public static <Item> void forEach(Set<Item> set, ItemHandler<Item> ih) {
        if (set != null && set.size() > 0) {
            for (Item i : set) {
                ih.handle(i);
            }
        }
    }

    public static <Item> void forEachKey(Map<Item, ? extends Object> map, ItemHandler<Item> ih) {
        if (map != null && map.size() > 0) {
            for (Item i : map.keySet()) {
                ih.handle(i);
            }
        }
    }

    public static <Item> void forEachValue(Map<? extends Object, Item> map, ItemHandler<Item> ih) {
        if (map != null && map.size() > 0) {
            for (Item i : map.values()) {
                ih.handle(i);
            }
        }
    }

}
