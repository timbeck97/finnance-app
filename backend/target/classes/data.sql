
 insert into role(id, name) values (3,'ADMIN'), (4,'USER');

 insert into user_table(id, name, password, username) values (666, 'Tim Morgenstern Beck', '$2a$10$BxJ9e/Hm7GFhTv5MgIuHNeyenrljcKB38T2RhmZCEfHyThIImNdXK', 'timbeck97');
 insert into user_table(id, name, password, username) values (777, 'Usuario Dois', '$2a$10$BxJ9e/Hm7GFhTv5MgIuHNeyenrljcKB38T2RhmZCEfHyThIImNdXK', 'teste123');

 insert into user_table_roles(user_id, roles_id) values (666,3), (666,4);
 insert into user_table_roles(user_id, roles_id) values  (777,4);

 insert into gasto(id, descricao, categoria, forma_pagamento,tipo_gasto, valor, usuario_id, data) values
 (nextval('gasto_seq'),'Cerveja BDL','LASER','CARTAO','VARIAVEL', 30.50, 666, to_char(now(),'yyyyMM') ),
 (nextval('gasto_seq'),'Gasolina','GASOLINA','CARTAO', 'VARIAVEL',50, 666, to_char(now(),'yyyyMM')),
 (nextval('gasto_seq'),'Corte Cabelo','OUTROS','PIX','VARIAVEL',27, 666, to_char(now(),'yyyyMM')),
 (nextval('gasto_seq'),'Spotify','OUTROS','CARTAO','VARIAVEL',35, 666, to_char(now(),'yyyyMM')),
 (nextval('gasto_seq'),'Mega xis','LASER','CARTAO','VARIAVEL',21.50, 666, to_char(now(),'yyyyMM')),
 (nextval('gasto_seq'),'Prime Video','OUTROS','CARTAO','VARIAVEL',14.50, 666, to_char(now(),'yyyyMM')),
 (nextval('gasto_seq'),'Farmacia','SAUDE','CARTAO','VARIAVEL',30.50, 666, to_char(now(),'yyyyMM')),
 (nextval('gasto_seq'),'Cerveja BDL','LASER','PIX','VARIAVEL',40.40, 666, to_char(now(),'yyyyMM')),
 (nextval('gasto_seq'),'Faculdade','OUTROS','PIX','FIXO',815.00, 666, to_char(now(),'yyyyMM')),
 (nextval('gasto_seq'),'Academia','OUTROS','PIX','FIXO',97.00, 666, to_char(now(),'yyyyMM')),
 (nextval('gasto_seq'),'Tarifa Banco','OUTROS','PIX','FIXO',14.90, 666, to_char(now(),'yyyyMM')),
 (nextval('gasto_seq'),'GASTO MES ANTERIOR 1','OUTROS','PIX','FIXO',49.50, 666, to_char(now(),'yyyyMM')),
 (nextval('gasto_seq'),'GASTO MES ANTERIOR 2','OUTROS','PIX','FIXO',49.50, 666, to_char(now(),'yyyyMM')),
 (nextval('gasto_seq'),'Claro','OUTROS','PIX','FIXO',49.50, 666, to_char(now(),'yyyyMM'));



 insert into deposito(id, descricao, valor, usuario_id, data) values
 (nextval('deposito_seq'),'Pix Cerveja',30.50, 666, to_char(now(),'yyyyMM')),
 (nextval('deposito_seq'),'Churrasco Aniver',50, 666, to_char(now(),'yyyyMM')),
 (nextval('deposito_seq'),'Internet',27, 666,to_char(now(),'yyyyMM')),
 (nextval('deposito_seq'),'Cerveja BDL',35, 666, to_char(now(),'yyyyMM'));






