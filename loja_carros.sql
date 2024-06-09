CREATE DATABASE loja_carros;
USE loja_carros;

CREATE TABLE carros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    ano INT,
    preco DOUBLE
);
select * FROM carros;
insert into carros
(id, marca, modelo, ano, preco)
values
 ('01','ford','ford ka','2018','23.000');
