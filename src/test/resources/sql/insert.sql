insert into tb_usuarios(id, nome, email, senha)
values (101, 'Admin 100', 'admin101@test.com', '$2a$12$Cbqx1xRfm/kEOwjyt64rR.3sakFGvfcSPWcckHyQeA4QbPwjjjuCa' );

insert into tb_usuario_permissao(usuario_id, permissao_id)
values (101,1);
