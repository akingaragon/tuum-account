package com.tuum.account.entity;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbstractAuditable implements Serializable {

    private String createdBy;

    private Instant createdDate;

    private String updatedBy;

    private Instant updatedDate;
}

