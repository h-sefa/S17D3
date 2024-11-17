package com.workintech.zoo.exceptions;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ZooErrorResponse {
    private String message;
    private int status;
    private long timestamp;
}
