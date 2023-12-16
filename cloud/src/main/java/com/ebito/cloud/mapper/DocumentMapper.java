package com.ebito.cloud.mapper;

import com.ebito.cloud.model.entity.Document;
import com.ebito.cloud.model.response.PrintedGuids;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DocumentMapper {

    String  link = "/api/v1/forms/";
    @Mapping(target = "link", source = "fileName", qualifiedByName = "documentMapper")
    @Mapping(target = "name", source ="fileType")
    @Mapping(target = "pdfFileName", source = "fileName")
    PrintedGuids toDto(Document document);


    @Named("documentMapper")
    default String documentMapper(String fileName) {
        return link + fileName;
    }

}