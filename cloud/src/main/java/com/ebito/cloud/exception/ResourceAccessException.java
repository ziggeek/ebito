package com.ebito.cloud.exception;

import org.springframework.web.client.RestClientException;

public class ResourceAccessException extends RestClientException {
    public ResourceAccessException(String message) {
        super(message);

    }


}
