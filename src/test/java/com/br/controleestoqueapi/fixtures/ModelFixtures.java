package com.br.controleestoqueapi.fixtures;

import java.util.List;

public interface ModelFixtures<E> {
    E valid();
    E invalid();
    List<E> list(Integer size);
}
