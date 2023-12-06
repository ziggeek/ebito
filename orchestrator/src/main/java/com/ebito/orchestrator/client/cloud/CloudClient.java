package com.ebito.orchestrator.client.cloud;

import com.ebito.orchestrator.model.response.PrintedGuids;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(
        value = "cloud",
        url = "${app.feign.client.config.cloud.url}",
        path = "/api/v1/")
public interface CloudClient {

    @GetMapping("/{clientId}/get-client-references")
    ResponseEntity<List<PrintedGuids>> getClientReferences(@PathVariable("clientId") String clientId);
}
