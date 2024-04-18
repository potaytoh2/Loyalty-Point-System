package com.points.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

/*
 * Points account
 */
@Table(name= "Points")
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Points {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "userId")
    // private UUID userId;
    private String userId;

    @Column(name = "balance")
    private long balance;

}
