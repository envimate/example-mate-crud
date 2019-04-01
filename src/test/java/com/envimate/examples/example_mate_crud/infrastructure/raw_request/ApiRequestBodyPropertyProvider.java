package com.envimate.examples.example_mate_crud.infrastructure.raw_request;

import org.json.JSONObject;

public interface ApiRequestBodyPropertyProvider {
    void provide(final JSONObject jsonObject);
}
