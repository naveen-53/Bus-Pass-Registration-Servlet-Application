package com.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BusPassTest {

    @Test
    void testBusPassGettersSetters() {
        BusPass pass = new BusPass();
        
        pass.setId(1);
        pass.setName("John Doe");
        pass.setEmail("john@example.com");
        pass.setPassType("Monthly");
        pass.setStartDate("2025-01-01");
        pass.setEndDate("2025-01-31");
        
        assertEquals(1, pass.getId(), "getId should return set id value");
        assertEquals("John Doe", pass.getName(), "getName should return set name");
        assertEquals("john@example.com", pass.getEmail(), "getEmail should return set email");
        assertEquals("Monthly", pass.getPassType(), "getPassType should return set passType");
        assertEquals("2025-01-01", pass.getStartDate(), "getStartDate should return set startDate");
        assertEquals("2025-01-31", pass.getEndDate(), "getEndDate should return set endDate");
    }
}
