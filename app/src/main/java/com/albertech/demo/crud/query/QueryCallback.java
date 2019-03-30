package com.albertech.demo.crud.query;

import java.util.List;

public interface QueryCallback<Bean> {

    void onResult(String path, List<Bean> list);
}
