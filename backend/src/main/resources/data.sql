
insert into role(id, name) values (3,'ADMIN'), (4,'USER');

insert into user_table(id, name, password, username, cpf) values (666, 'Tim Morgenstern Beck', '$2a$10$BxJ9e/Hm7GFhTv5MgIuHNeyenrljcKB38T2RhmZCEfHyThIImNdXK', 'timbeck97','04263284003');
insert into user_table(id, name, password, username, cpf) values (777, 'Usuario Dois', '$2a$10$BxJ9e/Hm7GFhTv5MgIuHNeyenrljcKB38T2RhmZCEfHyThIImNdXK', 'teste123','04265485265');

insert into user_table_roles(user_id, roles_id) values (666,3), (666,4);
insert into user_table_roles(user_id, roles_id) values (777,3), (777,4);

insert into gasto(id, descricao, categoria, forma_pagamento, valor, usuario_id, data) values
(nextval('gasto_seq'),'Cerveja BDL','LASER','CARTAO',30.50, 666, now()),
(nextval('gasto_seq'),'Gasolina','GASOLINA','CARTAO',50, 666, now()),
(nextval('gasto_seq'),'Corte Cabelo','OUTROS','PIX',27, 666, now()),
(nextval('gasto_seq'),'Spotify','OUTROS','CARTAO',35, 666, now()),
(nextval('gasto_seq'),'Mega xis','LASER','CARTAO',21.50, 666, now()),
(nextval('gasto_seq'),'Prime Video','OUTROS','CARTAO',14.50, 666, now()),
(nextval('gasto_seq'),'Farmacia','SAUDE','CARTAO',30.50, 666, now()),
(nextval('gasto_seq'),'Cerveja BDL','LASER','PIX',40.40, 666, now());




