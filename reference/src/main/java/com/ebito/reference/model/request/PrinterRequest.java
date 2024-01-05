package com.ebito.reference.model.request;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class PrinterRequest {

    String templateName;
    Map<String, Object> bookmarks;
}
