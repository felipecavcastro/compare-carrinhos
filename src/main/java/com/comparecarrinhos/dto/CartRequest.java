package com.comparecarrinhos.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {
    private List<Long> productIds;
}
