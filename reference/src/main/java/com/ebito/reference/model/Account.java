package com.ebito.reference.model;

import com.ebito.reference.model.enumeration.Currency;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @Column(name = "account_number")
    private int accountNumber;

    @Column(name = "account_currency")
    @Enumerated(EnumType.STRING)
    private Currency accountCurrency;
}
