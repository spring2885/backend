#!/bin/bash
##############################################################################
# Executes the swagger command line tool.
##############################################################################

declare -r CODEGEN="java -jar swagger-codegen-cli.jar"
declare -r CODEGEN_MODELS="java -Dmodels -jar swagger-codegen-cli.jar"

show_usage() {
    echo "$0"
    echo "Usage: $0 command <options>"
    echo "Commands: json, html, models, generate"
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

model_codegen() {
  ${CODEGEN_MODELS} \
	generate \
	-l spring-mvc \
	-i ../swagger.yaml \
	-o ../server \
	-c codegen-config.json
}

case $command in
    "json")
	json_codegen "$@"
	exit 0
	;;
    "models")
	model_codegen "$@"
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



