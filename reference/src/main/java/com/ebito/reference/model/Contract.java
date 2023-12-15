package com.ebito.reference.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "contracts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int contractNumber;

    private LocalDate contractDate;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
}
