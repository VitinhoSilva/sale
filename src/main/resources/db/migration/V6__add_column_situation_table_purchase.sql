ALTER TABLE purchase ADD situation ENUM('CONFIRMED', 'CANCELED', 'INSUFFICIENT_STOCK') NOT NULL AFTER total;