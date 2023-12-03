package com.ebito.orchestrator.client.reference;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(
        value = "reference",
        url = "${feign.client.config.reference.url}/api/v1/")
public interface ReferenceClient {
}
