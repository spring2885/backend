# Various tools needed to work with Spring2885.

## Swagger Code Generators

Swagger has various code generators to do everything from generate the JSON 
file from the YAML to create server side and client side stub code implementing
the API.  The commandlines are a bit tedious, so we have a few handy wrapper
scripts to make their use trivial.

### Scripts

| Script Name  | Description |
|--------------|----------------------------------|
| download.sh  | Downloads the most recent swagger code generator command line tool |
| codegen.sh | Invokes the swagger command line tool |

### Examples:

````
./download.sh
./codegen.sh help
````
