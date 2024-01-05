package com.ebito.reference.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "contract_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "contract_number")
    private int contractNumber;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    @JsonIgnore
    private Client client;
}
