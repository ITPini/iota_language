package edu.aau.g404.api;

import edu.aau.g404.device.SmartDevice;

import java.util.List;

/**
 * Deserializer interface defines a method for deserializing a JSON strings into a list of objects of type T.
 * @param <T> The type of object to deserialize from JSON, which must extend SmartDevice.
 */
public interface Deserializer<T extends SmartDevice> {
    /**
     * Deserialize a JSON string into a list of objects of type T.
     * @param jsonResponse  The JSON string to deserialize.
     * @return              A list of objects of type T, deserialized from JSON string.
     */
    List<T> deserialize(String jsonResponse);
}