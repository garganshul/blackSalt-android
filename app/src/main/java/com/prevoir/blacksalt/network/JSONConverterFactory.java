package com.prevoir.blacksalt.network;

/**
 * Created by anshul.garg on 31/08/17.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public final class JSONConverterFactory extends Converter.Factory {

    JSONConverterFactory() {
    }

    public static JSONConverterFactory create() {
        return new JSONConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        return (Converter) (type == JSONObject.class ? JSONResponseBodyConverter.INSTANCE
                : (type == JSONArray.class ? JSONArrayResponseBodyConverter.INSTANCE : null));
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        Converter<?, RequestBody> result = (type == JSONObject.class
                || type == JSONArray.class) ? JSONRequestBodyConverter.INSTANCE : null;


        return result;
    }

    static class JSONRequestBodyConverter implements Converter<Object, RequestBody> {
        private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private static final Charset UTF_8 = Charset.forName("UTF-8");
        static JSONConverterFactory.JSONRequestBodyConverter INSTANCE = new JSONConverterFactory.JSONRequestBodyConverter();

        JSONRequestBodyConverter() {
        }

        @Override
        public RequestBody convert(Object value) throws IOException {
            String body = (null != value) ? value.toString() : "{}";
            return RequestBody.create(MEDIA_TYPE, body.getBytes(UTF_8));
        }
    }

    static final class JSONResponseBodyConverter implements Converter<ResponseBody, JSONObject> {
        static final JSONConverterFactory.JSONResponseBodyConverter INSTANCE = new JSONConverterFactory.JSONResponseBodyConverter();

        JSONResponseBodyConverter() {
        }

        public JSONObject convert(ResponseBody value) throws IOException {
            if (null != value) {
                try {
                    return new JSONObject(value.string());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    static final class JSONArrayResponseBodyConverter implements Converter<ResponseBody, JSONArray> {
        static final JSONConverterFactory.JSONArrayResponseBodyConverter INSTANCE = new JSONConverterFactory.JSONArrayResponseBodyConverter();

        JSONArrayResponseBodyConverter() {
        }

        public JSONArray convert(ResponseBody value) throws IOException {
            if (null != value) {
                try {
                    return new JSONArray(value.string());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
