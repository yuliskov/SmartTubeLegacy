package com.liskovsoft.common.configparser;

public interface ConfigParser {
    String get(String key);
    String[] getArray(String key);
}
