package com.comparecarrinhos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CartCalculationTest {

    @Test
    @DisplayName("Deve calcular corretamente a soma de preço vezes quantidade")
    void deveCalcularSomaCorretamente() {
        // GIVEN (Dado que...)
        Double precoUnitario = 5.50;
        Integer quantidade = 3;
        Double valorEsperado = 16.50;

        // WHEN (Quando eu executo a conta...)
        Double resultado = precoUnitario * quantidade;

        // THEN (Então o resultado deve ser...)
        assertEquals(valorEsperado, resultado, "A conta de 5.50 * 3 deveria ser 16.50");
    }
}
