package com.tuum.account.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Data
public class AbstractAuditable {

    private String createdBy;

    private Instant createdDate;

    private String updatedBy;

    private Instant updatedDate;
}

