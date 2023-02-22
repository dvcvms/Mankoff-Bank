package ru.evsmanko.mankoff.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "MCC")
public class MCCInfoEntity {

    @Id
    private Long id;

    private Long mccCode;
    private String mccName;
    private String mccCodeDescription;
    private int cashbackPercent;
}
