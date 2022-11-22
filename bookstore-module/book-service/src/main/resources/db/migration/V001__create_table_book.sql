CREATE TABLE book (
  id INT NOT NULL AUTO_INCREMENT,
  author VARCHAR(70) NOT NULL,
  launch_date DATETIME NOT NULL,
  price DECIMAL(8,2) NOT NULL,
  title VARCHAR(100) NOT NULL,
  currency VARCHAR(3) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY id_UNIQUE (id)
  ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
  
  INSERT INTO book (author, launch_date, price, title, currency) VALUES
  	('Michael C. Feathers', '2017-11-29 13:50:05.878000', 8.57, 'Working effectively with legacy code', 'USD'),
  	('Ralph Johnson, Erich Gamma, John Vlissides e Richard Helm', '2017-11-29 15:15:13.636000', 7.87, 'Design Patterns', 'USD'),
  	('Robert C. Martin', '2009-01-10 00:00:00.000000', 13.46, 'Clean Code', 'USD'),
  	('Crockford', '2017-11-07 15:09:01.674000', 11.71, 'JavaScript', 'USD'),
  	('Steve McConnell', '2017-11-07 15:09:01.674000', 10.14, 'Code complete', 'USD'),
  	('Martin Fowler e Kent Beck', '2017-11-07 15:09:01.674000', 15.38, 'Refactoring', 'USD'),
  	('Eric Freeman, Elisabeth Freeman, Kathy Sierra, Bert Bates', '2017-11-07 15:09:01.674000', 19.23, 'Head First Design Patterns', 'USD'),
  	('Eric Evans', '2017-11-07 15:09:01.674000', 16.09, 'Domain Driven Design', 'USD'),
  	('Brian Goetz e Tim Peierls', '2017-11-07 15:09:01.674000', 13.99, 'Java Concurrency in Practice', 'USD'),
  	('Susan Cain', '2017-11-07 15:09:01.674000', 21.51, 'O poder dos quietos', 'USD'),
  	('Roger S. Pressman', '2017-11-07 15:09:01.674000', 9.79, 'Engenharia de Software: uma abordagem profissional', 'USD'),
  	('Viktor Mayer-Schonberger e Kenneth Kukier', '2017-11-07 15:09:01.674000', 9.44, 'Big Data: como extrair volume, variedade, velocidade e valor da avalanche de informação cotidiana', 'USD'),
  	('Richard Hunter e George Westerman', '2017-11-07 15:09:01.674000', 16.61, 'O verdadeiro valor de TI', 'USD'),
  	('Marc J. Schiller', '2017-11-07 15:09:01.674000', 7.87, 'Os 11 segredos de líderes de TI altamente influentes', 'USD'),
  	('Aguinaldo Aragon Fernandes e Vladimir Ferraz de Abreu', '2017-11-07 15:09:01.674000', 9.44, 'Implantando a governança de TI', 'USD');