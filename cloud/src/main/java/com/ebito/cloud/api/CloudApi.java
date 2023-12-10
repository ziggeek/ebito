package com.ebito.cloud.api;

import com.ebito.cloud.model.response.PrintedGuids;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Cloud", description = "Cloud API")
@RequestMapping("/api/v1")
public interface CloudApi {

    @GetMapping("/get-reference/{link}")
    //todo должен вернуть файл (этот метод только фронт вызывает, оркестратору не нужен этот метод)
    ResponseEntity<Resource> getReferenceByLink(@PathVariable("link") String link);
    // Комент от Артура: я буду тебе передавать в названии файла текст с именем файла для поля PrintedGuids.name
    // - разберешься как из MultipartFile извлекать
    @PostMapping("/{clientId}/save-client-reference")
    ResponseEntity<PrintedGuids> saveClientReference(@PathVariable("clientId") String clientId,
                                                     @RequestPart(name = "file")MultipartFile file);

    @GetMapping("/{clientId}/get-client-references")
    ResponseEntity<List<PrintedGuids>> getClientReferences(@PathVariable("clientId") String clientId);



}
