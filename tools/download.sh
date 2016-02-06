#!/bin/bash
##############################################################################
# Command to download the swagger codegen command line tool.
##############################################################################

curl http://repo1.maven.org/maven2/io/swagger/swagger-codegen-cli/2.1.5/swagger-codegen-cli-2.1.5.jar -o swagger-codegen-cli.jar || echo "Failed to download the file."

