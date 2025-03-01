package com.gianmarques.estoqueapi.entity.enums;

public enum ECategoriaProduto {
    LIMPEZA,
    BEBIDAS,
    MERCEARIA,
    PADARIA,
    HORTIFRUTI,
    ELETROPORTATEIS,
    ;

    ECategoriaProduto() {
    }

    public static ECategoriaProduto getCategoria(String categoria) {
        return ECategoriaProduto.valueOf(categoria);
    }
}
