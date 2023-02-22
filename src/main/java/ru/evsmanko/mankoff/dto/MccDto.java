package ru.evsmanko.mankoff.dto;

import lombok.Data;

@Data
public class MccDto {
    private Long id;

    private Long mccCode;
    private String mccName;
    private String mccCodeDescription;
    private int cashbackPercent;
}
