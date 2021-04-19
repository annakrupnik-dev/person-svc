package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 *
 * @author IHH-Dor Naim
 *
 */
public class JsonUtils {

    /**
     *
     * @param objectToConvert
     *            or collection of object
     * @return String
     */
    public static String convertObjectToJson(Object objectToConvert) {
        String json = "";

        try {
            if (objectToConvert != null) {
                ObjectMapper mapper = new ObjectMapper();
// convert object to JSON String
                json = mapper.writeValueAsString(objectToConvert);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed converting Object of type - "
                    + objectToConvert.getClass().getSimpleName() + ", to Json string", e);
        }
        return json;
    }
/*
    @SuppressWarnings({ "unchecked" })
    public static <T extends Object> T fromJsonString(String dataString,
                                                                  Class<? extends Object> clazz) {
        return (T) jsonString(dataString, null, clazz, null);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T extends List<? extends Object>> T fromJsonString(String dataString,
                                                                                  Class collectionClass,
                                                                                  Class<? extends Object> clazz) {
        return (T) jsonString(dataString, collectionClass, clazz, null);
    }

    static Object jsonString(String dataString,
                             Class<? extends Collection<? extends AbstractDataObject>> collectionClass,
                             Class<? extends AbstractDataObject> clazz, TypeReference<? extends AbstractDataObject> type) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            if (collectionClass != null) {
                CollectionType constructCollectionType = mapper.getTypeFactory()
                        .constructCollectionType(collectionClass, clazz);
                return mapper.readValue(dataString, constructCollectionType);
            } else {
                if (clazz.isAssignableFrom(GeneralResponseObject.class)) {
                    return mapper.readValue(dataString, type);
                }
                return mapper.readValue(dataString, clazz);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

 */
}

