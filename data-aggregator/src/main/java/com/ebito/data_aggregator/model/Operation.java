package com.ebito.data_aggregator.model;

import com.ebito.data_aggregator.model.enumeration.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "operations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Operation {

    @Id
    @Column(name = "operation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "sum")
    private long sum;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    @JsonIgnore
    private Client client;
}
