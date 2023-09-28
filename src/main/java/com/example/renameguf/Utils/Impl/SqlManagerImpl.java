package com.example.renameguf.Utils.Impl;

import com.example.renameguf.Utils.SqlManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SqlManagerImpl implements SqlManager {

    @Value("${sqlReq}")
    private String sqlRequest;

    @Override
    public String createUuidSqlRequest(String uuidList, String host) {
        return sqlRequest.replace("$uuidList)", uuidList).replace("$host" , host);
    }
}
