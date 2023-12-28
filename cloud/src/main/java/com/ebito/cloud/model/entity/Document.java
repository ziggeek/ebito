package com.ebito.cloud.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "t_documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "Cloud_id")
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

}
