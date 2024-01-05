package com.ebito.printed_form.template;

import com.ebito.printed_form.api.controller.request.FormGenerationRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YargWrapper {

    private final FormGenerationRequest main;
}
