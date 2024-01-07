package com.ebito.cloud.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "t_documents")
public class DocumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Cloud_id")
    private Long id;
    @Column(name = "client_id")
    private String clientId;

    @Column(name = "name_file")
    private String fileName;

    //Конструктор для создания объекта класса Document без id.

    public DocumentEntity(String clientId, String pdfFileName) {
        this.clientId = clientId;
        this.fileName = pdfFileName;
    }

}
