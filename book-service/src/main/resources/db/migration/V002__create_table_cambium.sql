CREATE TABLE cambium (
id int NOT NULL AUTO_INCREMENT,
from_currency char(3) NOT NULL,
to_currency char(3) NOT NULL,
conversion_factor decimal(65,2) NOT NULL,
PRIMARY KEY (id),
UNIQUE KEY id_UNIQUE (id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO cambium (id, from_currency, to_currency, conversion_factor) VALUES
(1, 'USD', 'BRL', 5.73),
(2, 'USD', 'EUR', 0.84),
(3, 'USD', 'GBP', 0.73),
(4, 'USD', 'ARS', 92.56),
(5, 'USD', 'CLP', 713.30),
(6, 'USD', 'COP', 3665.00),
(7, 'USD', 'MXN', 20.15);