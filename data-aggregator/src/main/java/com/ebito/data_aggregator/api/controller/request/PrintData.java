package com.ebito.data_aggregator.api.controller.request;

import com.ebito.data_aggregator.model.enumeration.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public abstract class PrintData {
    private String form;
    private String templateName;
    private Channel channel;
}
