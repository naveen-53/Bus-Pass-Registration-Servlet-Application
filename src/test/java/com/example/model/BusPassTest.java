package com.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        
        assertEquals(1, pass.getId(), "getId should return set id value");
        assertEquals("Naveen", pass.getName(), "getName should return set name");
        assertEquals("naveen123@example.com", pass.getEmail(), "getEmail should return set email");
        assertEquals("AC", pass.getPassType(), "getPassType should return set passType");
        assertEquals("2025-01-02", pass.getStartDate(), "getStartDate should return set startDate");
        assertEquals("2025-02-02", pass.getEndDate(), "getEndDate should return set endDate");
    }
}
