package com.infoshare.utils;

public interface Hasher {

    String hash(String password);

    boolean checkPassword(String password, String token);
}
