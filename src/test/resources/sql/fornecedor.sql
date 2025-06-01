insert into tb_usuarios (email, nome, senha, id)
values ('fornecedor201@test.com', 'Fornecedor 201', '$2a$12$Cbqx1xRfm/kEOwjyt64rR.3sakFGvfcSPWcckHyQeA4QbPwjjjuCa',
        201);
insert into tb_fornecedores (telefone, id)
values ('1140028933', 201);
insert into tb_usuario_permissao (usuario_id, permissao_id)
values (201, 3);



