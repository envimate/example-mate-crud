package com.envimate.examples.example_mate_crud.infrastructure.raw_request;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CreateResourceRequestBuilder {
    private final JSONObject jsonObject = new JSONObject();

    public static ApiRequestBodyPropertyProvider resourceType(final String resourceType) {
        return jsonObject -> jsonObject.put("resourceType", resourceType);
    }

    public JSONObject build() {
        return this.jsonObject;
    }
}
