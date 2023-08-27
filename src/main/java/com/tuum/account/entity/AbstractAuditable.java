package com.tuum.account.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@NoArgsConstructor
@Data
public class AbstractAuditable implements Serializable {

    private String createdBy;

    private Instant createdDate;

    private String updatedBy;

    private Instant updatedDate;
}

