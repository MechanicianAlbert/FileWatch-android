package com.albertech.filewatch.core.query.cursor.impl.abs;

public abstract class DirectCursorFactory extends AbsCursorFactory {

    @Override
    protected boolean recursive() {
        return false;
    }
}
