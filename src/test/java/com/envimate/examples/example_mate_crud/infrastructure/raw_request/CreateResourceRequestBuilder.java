package com.envimate.examples.example_mate_crud.infrastructure.raw_request;

import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateResourceRequestBuilder {
    private final JSONObject jsonObject = new JSONObject();

    public static ApiRequestBodyPropertyProvider resourceType(final String resourceType) {
        return jsonObject -> jsonObject.put("type", resourceType);
    }

    public static ApiRequestBodyPropertyProvider organisationId(final String organisationId) {
        return jsonObject -> jsonObject.put("organisationId", organisationId);
    }

    public static ApiRequestBodyPropertyProvider attributes(final String attributes) {
        return jsonObject -> jsonObject.put("attributes", attributes);
    }

    public JSONObject build() {
        return this.jsonObject;
    }
}
