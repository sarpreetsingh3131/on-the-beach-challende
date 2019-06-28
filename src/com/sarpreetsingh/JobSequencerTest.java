package com.sarpreetsingh;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class JobSequencerTest {

    private JobSequencer sut;
    private Map<Character, Job> jobs;

    @Before
    public void setUp() {
        this.jobs = new LinkedHashMap<>();
        this.sut = new JobSequencer(this.jobs);
    }

    @Test
    public void shouldReturnEmptyString() {
        assertEquals("", this.sut.findSequence(""));
    }

    @Test
    public void shouldReturnNull() {
        assertNull(this.sut.findSequence(null));
    }

    @Test
    public void shouldReturnCircularDependencyError() {
        for (String jobStructure : new String[] {
                "a => b, b => a",
                "a => b, b => c, c => a",
                "a =>, b => c, c => f, d => a, e =>, f => b"
        }) {
            assertEquals(JobSequencer.CIRCULAR_DEPENDENCY_ERROR, this.sut.findSequence(jobStructure));
            this.jobs.clear();
        }
    }

    @Test
    public void shouldReturnItselfDependencyError() {
        for (String jobStructure : new String[] {
                "a => b, b => b",
                "a => a",
                "a =>, b =>, c => c"
        }) {
            assertEquals(JobSequencer.ITSELF_DEPENDENCY_ERROR, this.sut.findSequence(jobStructure));
        }
    }

    @Test
    public void shouldReturnSequence() {
        Map<String, String> sequences = new LinkedHashMap<String, String>(){{
            put("a =>", "a");
            put("a =>, b =>, c =>", "cba");
            put("a =>, b => c, c =>", "cba");
            put("a =>, b => c, c => f, d => a, e => b, f =>", "fcbead");
            put("a => b, b => d, b => c, c => e", "ecdba");
        }};

        for (String key : sequences.keySet()) {
            assertEquals(sequences.get(key), this.sut.findSequence(key));
        }
    }
}