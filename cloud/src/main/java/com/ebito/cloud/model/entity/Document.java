package com.ebito.cloud.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "t_documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "type_file")
    private String fileType;
    @Column(name = "name_file")
    private String fileName;

    //Конструктор для создания объекта класса Document без id.


    public Document(String clientId, String type, String pdfFileName) {
        this.clientId = clientId;
        this.fileType = type;
        this.fileName = pdfFileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(id, document.id) && Objects.equals(clientId, document.clientId) && Objects.equals(fileType, document.fileType) && Objects.equals(fileName, document.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, fileType, fileName);
    }
}
