#!/usr/bin/env bash

set -e
set -u

help_screen() {
    echo -e "usage: ./create_service.sh [-f|--recreate]"
}

while [[ $# -gt 0 ]]
do
key="$1"

case $key in
    -f|--recreate)
    RECREATE=true
    ;;
    *)
    echo "unexpected value"
    help_screen
    exit 1;
    ;;
esac
shift # past argument or value
done

source template_env.sh env.sh

env_var_value() {
    local varName=""
    varName="${1}"
    echo $(eval "echo \$${varName}")
}

construct_parameter_string() {
    local cf_params=""
    declare -a paramNames=("VpcId" "PublicSubnetId" "AlternatePublicSubnetId" "Image" "Cpu" "Memory" "Cluster" "JavaOpts")
    for param in "${paramNames[@]}"
    do
        if [[ -v param ]]
        then
            cf_params="${cf_params} ParameterKey=${param},ParameterValue="$(env_var_value ${param})""
        fi
    done
    echo ${cf_params}
}

create_stack() {
    local cf_params=$(construct_parameter_string)

    echo ${cf_params}

    aws cloudformation create-stack \
        --stack-name "${StackName}" \
        --template-body file://../aws/example-mate-crud-service.yaml \
        --parameters ${cf_params} \
        --capabilities CAPABILITY_NAMED_IAM
}

delete_stack() {
    aws cloudformation delete-stack \
        --stack-name "${StackName}"
}
delete_stack
create_stack
