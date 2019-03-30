package com.albertech.demo.util.query;

import java.util.List;

public interface QueryCallback<Bean> {

    void onResult(String path, List<Bean> list);
}
