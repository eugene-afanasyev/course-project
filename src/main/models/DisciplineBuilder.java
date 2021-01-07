package main.models;

public class DisciplineBuilder {
    private final Discipline discipline;

    public DisciplineBuilder(){
        discipline = new Discipline();
    }

    public DisciplineBuilder withName(String name){
        discipline.setName(name);
        return this;
    }

    public DisciplineBuilder withDescription(String description){
        discipline.setDescription(description);
        return this;
    }

    public DisciplineBuilder withCode(String code){
        discipline.setDisciplineCode(code);
        return this;
    }

    public Discipline Build(){
        return discipline;
    }
}
