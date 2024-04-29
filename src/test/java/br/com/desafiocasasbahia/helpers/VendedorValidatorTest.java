package br.com.desafiocasasbahia.helpers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class VendedorValidatorTest {
    /**
     * Method under test: {@link VendedorValidator#validaData(String)}
     */
    @Test
    void testValidaData() {
        // Arrange, Act and Assert
        assertFalse(VendedorValidator.validaData("Data"));
        assertTrue(VendedorValidator.validaData("09/09/9999"));
    }
}
