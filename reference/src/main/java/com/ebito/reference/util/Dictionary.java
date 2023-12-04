package com.ebito.reference.util;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {

    private static final Map<String, String> REFERENCE_NAME_MAP = new HashMap<>();

    static {
        REFERENCE_NAME_MAP.put("001", "Выписка о начислениях абонента");
    }

    public static String getReferenceName(String typeCode) {
        return REFERENCE_NAME_MAP.get(typeCode);
    }
}
