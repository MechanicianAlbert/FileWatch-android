package com.albertech.filewatch.core.query;

import java.util.List;

public interface IFileQureyListener {

    void onQueryResult(String path, List<String> list);
}
