package com.ebito.reference.model;

import com.ebito.reference.model.enumeration.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    @JsonIgnore
    private Client client;

    @Column(name = "account_number")
    private int accountNumber;

    @Column(name = "account_currency")
    @Enumerated(EnumType.STRING)
    private Currency accountCurrency;
}
