package com.ebito.orchestrator.client.cloud;


import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(
        value = "cloud",
        url = "${feign.client.config.cloud.url}/api/v1/")
public interface CloudClient {
}
