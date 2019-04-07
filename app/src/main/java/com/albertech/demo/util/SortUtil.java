package com.albertech.demo.util;

import com.albertech.demo.base.bean.BaseFileBean;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortUtil {

    public interface SortType {

        int SORT_BY_ALPHABET = 0;
        int SORT_BY_DATE = 1;
        int SORT_BY_SUFFIX = 2;
        int SORT_BY_SIZE_INCREASE = 3;
        int SORT_BY_SIZE_DECREASE = 4;

    }


    private static final Comparator<BaseFileBean> COMPARATOR_ALPHABET = new Comparator<BaseFileBean>() {

        @Override
        public int compare(BaseFileBean o1, BaseFileBean o2) {
            if (o1.isDirectory() ^ o2.isDirectory()) {
                return o1.isDirectory() ? -1 : 1;
            } else {
                return o1.name.compareTo(o2.name);
            }
        }
    };

    private static final Comparator<BaseFileBean> COMPARATOR_DATE = new Comparator<BaseFileBean>() {

        @Override
        public int compare(BaseFileBean o1, BaseFileBean o2) {
            if (o1.isDirectory() ^ o2.isDirectory()) {
                return o1.isDirectory() ? -1 : 1;
            } else {
                return Long.compare(o1.date, o2.date);
            }
        }
    };

    private static final Comparator<BaseFileBean> COMPARATOR_SUFFIX = new Comparator<BaseFileBean>() {

        @Override
        public int compare(BaseFileBean o1, BaseFileBean o2) {
            if (o1.isDirectory() ^ o2.isDirectory()) {
                return o1.isDirectory() ? -1 : 1;
            } else {
                if (o1.isDirectory()) {
                    return o1.name.compareTo(o2.name);
                } else {
                    return o1.suffix.compareTo(o2.suffix);
                }
            }
        }
    };

    private static final Comparator<BaseFileBean> COMPARATOR_SIZE_INCREASE = new Comparator<BaseFileBean>() {

        @Override
        public int compare(BaseFileBean o1, BaseFileBean o2) {
            if (o1.isDirectory() ^ o2.isDirectory()) {
                return o1.isDirectory() ? -1 : 1;
            } else {
                if (o1.isDirectory()) {
                    return o1.name.compareTo(o2.name);
                } else {
                    return Long.compare(o1.size, o2.size);
                }
            }
        }
    };

    private static final Comparator<BaseFileBean> COMPARATOR_SIZE_DECREASE = new Comparator<BaseFileBean>() {

        @Override
        public int compare(BaseFileBean o1, BaseFileBean o2) {
            if (o1.isDirectory() ^ o2.isDirectory()) {
                return o1.isDirectory() ? -1 : 1;
            } else {
                if (o1.isDirectory()) {
                    return o1.name.compareTo(o2.name);
                } else {
                    return Long.compare(o2.size, o1.size);
                }
            }
        }
    };


    public static void sortBy(List<? extends BaseFileBean> list, int sortBy) {
        Comparator<BaseFileBean> comparator = getComparator(sortBy);
        Collections.sort(list, comparator);
    }


    private static Comparator<BaseFileBean> getComparator(int sortBy) {
        switch (sortBy) {
            case SortType.SORT_BY_ALPHABET:
                return COMPARATOR_ALPHABET;
            case SortType.SORT_BY_DATE:
                return COMPARATOR_DATE;
            case SortType.SORT_BY_SUFFIX:
                return COMPARATOR_SUFFIX;
            case SortType.SORT_BY_SIZE_INCREASE:
                return COMPARATOR_SIZE_INCREASE;
            case SortType.SORT_BY_SIZE_DECREASE:
                return COMPARATOR_SIZE_DECREASE;
            default:
                return COMPARATOR_ALPHABET;
        }
    }

}
