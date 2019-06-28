package com.sarpreetsingh;

import java.util.LinkedHashSet;
import java.util.Set;

class Job {

    private char name;
    private int number;
    private Set<Job> successors;

    Job(char name) {
        this.name = name;
        this.successors = new LinkedHashSet<>();
    }

    boolean hasSuccessor(Job job) {
        return this.successors.contains(job);
    }

    void addSuccessor(Job successor) {
        this.successors.add(successor);
    }

    void setNumber(int number) {
        this.number = number;
    }

    char getName() {
        return this.name;
    }

    int getNumber() {
        return this.number;
    }

    Set<Job> getSuccessors() {
        return this.successors;
    }
}
