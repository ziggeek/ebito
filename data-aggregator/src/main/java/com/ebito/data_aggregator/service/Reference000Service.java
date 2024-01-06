package com.ebito.data_aggregator.service;

import com.ebito.data_aggregator.api.controller.response.PrintedGuids;
import com.ebito.data_aggregator.model.PrintRequest;

public abstract class Reference000Service {
    public abstract PrintedGuids execute(PrintRequest printRequest);
}

