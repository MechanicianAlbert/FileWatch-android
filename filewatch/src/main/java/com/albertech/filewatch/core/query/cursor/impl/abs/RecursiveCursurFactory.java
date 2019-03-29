package com.albertech.filewatch.core.query.cursor.impl.abs;

public abstract class RecursiveCursurFactory extends AbsCursorFactory {

    @Override
    protected boolean recursive() {
        return true;
    }
}
