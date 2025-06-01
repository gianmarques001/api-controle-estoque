insert into tb_usuarios (email, nome, senha, id)
values ('estoquista202@test.com', 'Estoquista 202', '$2a$12$Cbqx1xRfm/kEOwjyt64rR.3sakFGvfcSPWcckHyQeA4QbPwjjjuCa',
        202);
insert into tb_estoquistas (id)
values (202);
insert
into tb_usuario_permissao (usuario_id, permissao_id)
values (202, 2);