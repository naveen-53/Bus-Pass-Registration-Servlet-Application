package com.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BusPassTest {

    @Test
    void testBusPassGettersSetters() {
        BusPass pass = new BusPass();
        
        pass.setId(1);
        pass.setName("Naveen");
        pass.setEmail("naveen123@example.com");
        pass.setPassType("AC");
        pass.setStartDate("2025-01-02");
        pass.setEndDate("2025-02-02");
        
        assertEquals(1, pass.getId());
        assertEquals("Naveen", pass.getName());
        assertEquals("naveen123@example.com", pass.getEmail());
        assertEquals("AC", pass.getPassType());
        assertEquals("2025-01-02", pass.getStartDate());
        assertEquals("2025-02-02", pass.getEndDate());
    }
}
