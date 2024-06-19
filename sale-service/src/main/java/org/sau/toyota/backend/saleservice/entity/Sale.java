package org.sau.toyota.backend.saleservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sau.toyota.backend.saleservice.Enum.PaymentType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
/** @author Ahmet Alıç
 * @since 15-06-2024
 *
 * Sale is an entity class that represents a sale transaction within the system.
 *
 * <p>
 * This class maps to the "sale" table in the database and includes details such as
 * the total amount of the sale, sale time, cashier name, received amount, change amount,
 * and the type of payment. It also defines relationships with the {@link SoldProduct} entity.
 * </p>
 *
 * <p>
 * The class also uses JPA annotations to define the table mapping and relationships between entities.
 * </p>
 *
 * @see SoldProduct
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="sale")
@Builder
public class Sale {
    /**
     * The unique identifier for the {@link Sale} entity.
     * The implementing strategy is defined as identity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The list of products sold in this sale.
     * This is a one-to-many relationship with the {@link SoldProduct} entity.
     * Each sale can have multiple sold products.
     */
    @OneToMany(mappedBy = "sale",
            cascade = CascadeType.ALL)
    private List<SoldProduct> soldProducts;
    /**
     * The total amount of the sale which is not nullable and can have maximum length of 50 characters.
     */
    @Column(name = "total_amount", length = 50, nullable = false)
    private double totalAmount;
    /**
     * The date and time of the sale which is not nullable and can have maximum length of 100 characters.
     */
    @Column(name = "sale_time", length = 100, nullable = false)
    private LocalDateTime saleTime;
    /**
     * The time of the sale which is not nullable and can have maximum length of 50 characters.
     */
    @Column(name = "time", length = 50)
    private LocalTime time;
    /**
     * The name of the cashier who processed the sale which can have maximum length of 50 characters.
     */
    @Column(name = "cashier_name", length = 50)
    private String cashierName;
    /**
     * The amount received from the customer which is not nullable and can have maximum length of 50 characters.
     */
    @Column(name = "received_amount", length = 50, nullable = false)
    private double receivedAmount;
    /**
     * The amount of change returned to the customer which is not nullable and can have maximum length of 50 characters.
     */
    @Column(name = "change_amount", length = 50, nullable = false)
    private double changeAmount;

    /**
     * The payment type used for the sale (e.g., cash, credit card) which is not nullable and can have maximum length of 50 characters.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", length = 50, nullable = false)
    private PaymentType paymentType;

}