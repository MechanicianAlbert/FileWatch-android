package com.albertech.demo.util.query;

import java.util.List;

public interface QueryCallback {

    void onResult(String path, List<String> list);
}
