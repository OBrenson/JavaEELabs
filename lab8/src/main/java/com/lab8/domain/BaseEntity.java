package com.lab8.domain;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name="id_generator", sequenceName = "id_seq")
    @Column(name = "id", updatable = false, nullable = false, insertable = false)
    private Long id;

    public BaseEntity() {
    }

    @Column(name = "name")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    protected BaseEntity (BaseBuilder builder) {
        this.name = builder.name;
    }

    protected abstract static class BaseBuilder {

        private String name;

        protected BaseBuilder(String name) {
            this.name = name;
        }

        protected abstract BaseEntity build();
    }
}
