#!/bin/bash
##############################################################################
# Executes the swagger command line tool.
##############################################################################

declare -r CODEGEN="java -jar swagger-codegen-cli.jar"

show_usage() {
    echo "$0"
    echo "Usage: $0 command <options>"
    echo "Commands: json, html, generate"
    echo "json: regenerates the swagger.json file"
    echo "html: regenerates static HTML documentation"
    echo "generate: invokes the swagger codegen tool. "
    echo "          See https://github.com/swagger-api/swagger-codegen/"
}

if [ $# -lt 1 ]; then
    show_usage
    exit 1
fi

declare -r command=$1
shift

json_codegen() {
    ${CODEGEN} \
	generate \
	-l swagger \
	-i ../swagger.yaml \
	-o ../server/src/main/webapp/
}

html_codegen() {
    ${CODEGEN} \
	generate \
	-l html \
	-i ../swagger.yaml \
	-o ../server/src/main/webapp/doc
}

default_codegen() {
    ${CODEGEN} $command \
	-i ../swagger.yaml \
	"$@"
}

case $command in
    "json")
	json_codegen "$@"
	exit 0
	;;
    "html")
	html_codegen "$@"
	exit 0
	;;
    *)
	default_codegen "$@"
	exit 0
	;;
esac



