package com.ebito.reference.service;

import com.ebito.reference.api.controller.response.PrintedGuids;
import com.ebito.reference.model.PrintRequest;

public abstract class Reference000Service {
    public abstract PrintedGuids execute(PrintRequest printRequest);
}

