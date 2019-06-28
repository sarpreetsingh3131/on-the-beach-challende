package com.sarpreetsingh;

import java.util.*;

class JobSequencer {

    public static String ITSELF_DEPENDENCY_ERROR = "jobs can’t depend on themselves";
    public static String CIRCULAR_DEPENDENCY_ERROR = "jobs can’t have circular dependencies";

    private Map<Character, Job> jobs;

    JobSequencer(Map<Character, Job> jobs) {
        this.jobs = jobs;
    }

    String findSequence(String jobStructure) {
        if (jobStructure == null || jobStructure.isEmpty()) {
            return jobStructure;
        }
        this.jobs.clear();
        this.addJobs(jobStructure);
        return this.sortJobsInTopologicalOrder();
    }

    private void addJobs(String jobStructure) {
        for (String value : jobStructure.split(",")) {
            value = value.trim();
            if (value.endsWith("=>")) {
                this.addJob(value.charAt(0));
            } else {
                this.addDependency(value.charAt(value.length() - 1), value.charAt(0));
            }
        }
    }

    private Job addJob(char name) {
        Job job = this.jobs.get(name);
        if (job == null) { // only add if not added before
            job = new Job(name);
            this.jobs.put(name, job);
        }
        return job;
    }

    private void addDependency(char from, char to) {
        Job source = this.addJob(from); // get source and target jobs
        Job target = this.addJob(to);
        boolean hasDependency = source.hasSuccessor(target);
        if (!hasDependency) { // only add if not added before
            source.addSuccessor(target);
        }
    }

    // Traverse the jobs in post order, i.e., visit children before parent
    private List<Job> getJobsInPostOrder() {
        Set<Job> visited = new LinkedHashSet<>();
        List<Job> jobs = new LinkedList<>();
        this.jobs.values().forEach(job -> this.postOrderTraverse(job, visited, jobs));
        return jobs;
    }

    private void postOrderTraverse(Job job, Set<Job> visited, List<Job> jobs) {
        if (!visited.contains(job)) {
            visited.add(job);
            job.getSuccessors().forEach(successor -> this.postOrderTraverse(successor, visited, jobs)); // visit children
            jobs.add(job); // add parent
            job.setNumber(jobs.size()); // set the number based on post order visit
        }
    }

    // I use topological sort algorithm that sort the jobs in specific order, i.e.,
    // dependent jobs always come after the jobs on which they are dependent on.
    // note: topological order cannot be performed if cycle is present.
    // therefore, we check for the cycle first. according to definition,
    // topological sort is the opposite of post order traverse
    private String sortJobsInTopologicalOrder() {
        List<Job> jobs = this.getJobsInPostOrder();

        for (Job job : jobs) {
            for (Job successor : job.getSuccessors()) {
                // check cycle
                if (job.getNumber() < successor.getNumber()) {
                    return CIRCULAR_DEPENDENCY_ERROR;
                } else if (job.getNumber() == successor.getNumber()) {
                    return ITSELF_DEPENDENCY_ERROR;
                }
            }
        }

        Collections.reverse(jobs);

        StringBuilder str = new StringBuilder();
        jobs.forEach(job -> str.append(job.getName()));
        return str.toString();
    }
}
