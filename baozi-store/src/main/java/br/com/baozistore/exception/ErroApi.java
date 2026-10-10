package br.com.baozistore.exception;

import java.time.Instant;

public record ErroApi(Instant timestamp, int status, String erro, String mensagem, String caminho) {}
