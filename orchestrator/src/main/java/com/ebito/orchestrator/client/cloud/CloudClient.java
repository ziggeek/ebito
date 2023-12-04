package com.ebito.orchestrator.client.cloud;

import com.ebito.orchestrator.model.response.PrintedGuids;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(
        value = "cloud",
        url = "${feign.client.config.cloud.url}")
@RequestMapping("/api/v1/")
public interface CloudClient {

    @GetMapping("/{clientId}/get-client-references")
    List<PrintedGuids> getAllClientReferences(@PathVariable("clientId") String clientId);
}
