package com.gianmarques.estoqueapi.dto.estoquista;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EstoquistaRequestDTO(@NotNull @Size(min = 5, max = 20) String nome, @Email String email,
                                   @NotNull @Size(min = 8, max = 50) String senha) {
}
