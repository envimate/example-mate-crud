package com.envimate.examples.example_mate_crud.infrastructure.raw_request;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UpdateResourceRequestBuilder {
    private final JSONObject jsonObject = new JSONObject();

    public static ApiRequestBodyPropertyProvider resourceType(final String resourceType) {
        return jsonObject -> jsonObject.put("type", resourceType);
    }
    public static ApiRequestBodyPropertyProvider id(final String id) {
        return jsonObject -> jsonObject.put("id", id);
    }

    public JSONObject build() {
        return this.jsonObject;
    }
}
