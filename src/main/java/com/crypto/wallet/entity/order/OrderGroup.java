package com.crypto.wallet.entity.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "ordergroups")
@Table(name = "ordergroups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_group_tuid")
    private String orderGroupTuid;
    private boolean isActive;
    @OneToMany(mappedBy = "orderGroup", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
    private long createTime;
    private long updateTime;
}
