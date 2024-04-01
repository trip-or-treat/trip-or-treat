package com.triportreat.backend.common.utils;

import java.util.UUID;

public class UuidUtil {

    public static String createUuidUpperCase() {
        return UUID.randomUUID().toString().toUpperCase();
    }
}
