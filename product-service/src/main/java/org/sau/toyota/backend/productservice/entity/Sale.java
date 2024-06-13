package org.sau.toyota.backend.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.toyota.backend.productservice.Enum.PaymentType;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="sale")
@Builder
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "sale",
            cascade = CascadeType.ALL)
    private List<SoldProduct> soldProducts;

    @Column(name = "total_amount", length = 50, nullable = false)
    private double totalAmount;

    @Column(name = "sale_time", length = 100, nullable = false)
    private LocalDateTime saleTime;

    @Column(name = "time", length = 50)
    private LocalTime time;

    @Column(name = "cashier_name", length = 50)
    private String cashierName;

    @Column(name = "received_amount", length = 50, nullable = false)
    private double receivedAmount;

    @Column(name = "change_amount", length = 50, nullable = false)
    private double changeAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", length = 50, nullable = false)
    private PaymentType paymentType;

}