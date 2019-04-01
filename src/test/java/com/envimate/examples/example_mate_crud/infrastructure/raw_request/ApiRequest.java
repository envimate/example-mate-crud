package com.envimate.examples.example_mate_crud.infrastructure.raw_request;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.json.JSONObject;

import java.util.Map;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ApiRequest {
    public final String urlTemplate;
    public JSONObject body;
    public final Map<String, String> routeParameters;
    public final Map<String, String> headers;
    public final String httpMethod;

    public static ApiRequest apiRequest(final String urlTemplate, final Map<String, String> routeParameters, final String httpMethod) {
        return new ApiRequest(urlTemplate, routeParameters, Map.of("Content-Type", "application/json"), httpMethod);
    }

    public static ApiRequest fetchResourceRequest(final String id) {
        return apiRequest("/api/resource/{id}", Map.of("id", id), "GET");
    }


    public static ApiRequest createResourceRequest() {
        return apiRequest("/api/resource", Map.of(), "POST");
    }


    public static ApiRequest listResourceRequest() {
        return apiRequest("/api/resource", Map.of(), "GET");
    }

    public ApiRequest with(final ApiRequestBodyPropertyProvider propertyProvider) {
        if(this.body == null) {
            this.body = new JSONObject();
        }
        propertyProvider.provide(this.body);
        return this;
    }

    public boolean hasBody() {
        return body != null;
    }
}
