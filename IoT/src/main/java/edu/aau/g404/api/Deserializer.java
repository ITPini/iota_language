package edu.aau.g404.api;

import java.util.List;

public interface Deserializer<T> {
    List<T> deserialize(String jsonResponse);
}
