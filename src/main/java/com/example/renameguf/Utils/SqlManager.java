package com.example.renameguf.Utils;

import org.springframework.stereotype.Component;

@Component
public interface SqlManager {
    String createUuidSqlRequest (String uuidList, String host);

}
