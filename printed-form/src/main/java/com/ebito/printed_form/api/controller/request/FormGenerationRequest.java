package com.ebito.printed_form.api.controller.request;

import com.ebito.printed_form.api.controller.request.reference.Reference001FormGenerationRequest;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "form")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Reference001FormGenerationRequest.class, name = "REFERENCE_001_BRANCH"),
        @JsonSubTypes.Type(value = Reference001FormGenerationRequest.class, name = "REFERENCE_001_ONLINE"),
        @JsonSubTypes.Type(value = Reference001FormGenerationRequest.class, name = "REFERENCE_001_MOBILE"),
})
public interface FormGenerationRequest extends FormSource {
}