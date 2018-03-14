package net.orekyuu.moco.core.relation;

import java.util.List;

public class Preloader<T> {

    public void preload(List<T> records, List<Relation<T>> relations) {
        for (Relation<T> relation : relations) {
            relation.preload(records);
        }
    }
}
