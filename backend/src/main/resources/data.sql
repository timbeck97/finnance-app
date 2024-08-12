
 insert into role(id, name) values (3,'ADMIN'), (4,'USER');

 insert into user_table(id, name, password, username) values (666, 'Tim Morgenstern Beck', '$2a$10$BxJ9e/Hm7GFhTv5MgIuHNeyenrljcKB38T2RhmZCEfHyThIImNdXK', 'timbeck97');
 insert into user_table(id, name, password, username) values (777, 'Usuario Dois', '$2a$10$BxJ9e/Hm7GFhTv5MgIuHNeyenrljcKB38T2RhmZCEfHyThIImNdXK', 'teste123');

 insert into user_table_roles(user_id, roles_id) values (666,3), (666,4);
 insert into user_table_roles(user_id, roles_id) values  (777,4);

 insert into gasto(id, descricao, categoria, forma_pagamento,tipo_gasto, valor, usuario_id, data) values
 (1,'Cerveja BDL','LASER','CARTAO','VARIAVEL', 30.50, 666, to_char(now(),'yyyyMM') ),
 (2,'Gasolina','GASOLINA','CARTAO', 'VARIAVEL',50, 666, to_char(now(),'yyyyMM')),
 (3,'Corte Cabelo','OUTROS','PIX','VARIAVEL',27, 666, to_char(now(),'yyyyMM')),
 (4,'Spotify','OUTROS','CARTAO','VARIAVEL',35, 666, to_char(now(),'yyyyMM')),
 (5,'Mega xis','LASER','CARTAO','VARIAVEL',21.50, 666, to_char(now(),'yyyyMM')),
 (6,'Prime Video','OUTROS','CARTAO','VARIAVEL',14.50, 666, to_char(now(),'yyyyMM')),
 (7,'Farmacia','SAUDE','CARTAO','VARIAVEL',30.50, 666, to_char(now(),'yyyyMM')),
 (8,'Cerveja BDL','LASER','PIX','VARIAVEL',40.40, 666, to_char(now(),'yyyyMM')),
 (9,'Faculdade','OUTROS','PIX','FIXO',815.00, 666, to_char(now(),'yyyyMM')),
 (10,'Academia','OUTROS','PIX','FIXO',97.00, 666, to_char(now(),'yyyyMM')),
 (11,'Tarifa Banco','OUTROS','PIX','FIXO',14.90, 666, to_char(now(),'yyyyMM')),
 (12,'GASTO MES ANTERIOR 1','OUTROS','PIX','FIXO',49.50, 666, to_char(now(),'yyyyMM')),
 (13,'GASTO MES ANTERIOR 2','OUTROS','PIX','FIXO',49.50, 666, to_char(now(),'yyyyMM')),
 (14,'Claro','OUTROS','PIX','FIXO',49.50, 666, to_char(now(),'yyyyMM')),
 (15,'Tarifa Banco','OUTROS','PIX','FIXO',14.90, 666, to_char(now(),'yyyyMM')),
 (16,'GASTO MES ANTERIOR 1','OUTROS','PIX','FIXO',49.50, 666, '202407'),
 (17,'GASTO MES ANTERIOR 2','OUTROS','PIX','FIXO',49.50, 666, '202405'),
 (18,'GASTO MES ANTERIOR 2','OUTROS','PIX','FIXO',49.50, 666, '202405'),
 (19,'Claro','OUTROS','PIX','FIXO',49.50, 666, '202406');

insert into pagamento(id, usuario_id, valor ,data) values
(1,666, 4000, '202401'),
(2,666, 4005, '202402'),
(3,666, 3980, '202403'),
(4,666, 4150, '202404'),
(5,666, 4500, '202405'),
(6,666, 4550, '202406'),
(7,666, 4300, '202407'),
(8,666, 4700, '202408'),
(9,666, 4700, '202409'),
(10,666, 4325, '202410'),
(11,666, 4425, '202411'),
(12,666, 4500, '202412');


 insert into deposito(id, descricao, valor, usuario_id, data) values
 (1,'Pix Cerveja',30.50, 666, to_char(now(),'yyyyMM')),
 (2,'Churrasco Aniver',50, 666, to_char(now(),'yyyyMM')),
 (3,'Internet',27, 666,to_char(now(),'yyyyMM')),
 (4,'Cerveja BDL',35, 666, to_char(now(),'yyyyMM'));






