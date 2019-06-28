package com.sarpreetsingh;

import java.util.LinkedHashMap;

public class Main {


    public static void main(String[] args) {
        // Please follow the below pattern to define the job structure
        if (args.length == 0) {
            args = new String[] {
                    "a =>",
                    "a =>, b =>, c =>",
                    "a =>, b => c, c =>",
                    "a =>, b => c, c => f, d => a, e => b, f =>",
                    "a => b, b => d, b => c, c => e",
                    "a =>, b => c, c => f, d => a, e =>, f => b",
                    "a => a"
            };
        }

        JobSequencer jobSequencer = new JobSequencer(new LinkedHashMap<>());

        for (String arg : args) {
            String sequence = jobSequencer.findSequence(arg);
            System.out.println(sequence);
        }
    }
}
