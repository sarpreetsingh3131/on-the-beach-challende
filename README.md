# on-the-beach-challenge

## how to run
1. clone or download the repository
2. open repository root folder in terminal
3. run jar file using 
    -  `java -jar on-the-beach-challenge.jar <jobs structure>`

*Note:* `<jobs structure>` must follow the following pattern:
- {job}{dependency arrow}{job(optional)}{semicolon}

- Example

```
java -jar on-the-beach-challenge.jar "a =>" "a =>, b =>, c =>" 


java -jar on-the-beach-challenge.jar "a =>, b => c, c =>"


java -jar on-the-beach-challenge.jar "a =>, b => c, c => f, d => a, e => b, f =>"
                    
                    
java -jar on-the-beach-challenge.jar "a => b, b => d, b => c, c => e"
                    

java -jar on-the-beach-challenge.jar "a =>, b => c, c => f, d => a, e =>, f => b"
 
 
 java -jar on-the-beach-challenge.jar "a => a"
```