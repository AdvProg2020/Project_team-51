package Server;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.YaGsonBuilder;

import java.io.BufferedReader;

public class JsonConverter {

    private static YaGson yaGson = new YaGsonBuilder().enableComplexMapKeySerialization().create();

    public static <T> String toJson(T object) {
        return yaGson.toJson(object)
                .replaceAll("\"\\w+\":(0|false),?", "")
                .replaceAll(",}", "}")
                .replaceAll(",]", "]");
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return yaGson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(BufferedReader reader, Class<T> classOfT) {
        return yaGson.fromJson(reader, classOfT);
    }

}
