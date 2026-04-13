package com.comparecarrinhos.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class CartRequest {
    private List<CartItemDTO> items;

    @Getter @Setter
    public static class CartItemDTO {
        private Long productId;
        private Integer quantity;
    }
}
