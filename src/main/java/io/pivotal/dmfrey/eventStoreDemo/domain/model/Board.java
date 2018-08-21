package io.pivotal.dmfrey.eventStoreDemo.domain.model;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Data;

@Data
public class Board {

    private String name;
    private Collection<Story> backlog = new ArrayList<>();

}
