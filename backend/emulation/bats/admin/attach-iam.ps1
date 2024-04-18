$func=Get-Content $PSScriptRoot/functionname.txt
$rolename="lambda-ex"
$arn=(aws iam create-role --role-name $rolename --assume-role-policy-document file://$PSScriptRoot/../../policies/AWSLambdaSQSQueueExecutionRole.json |
convertfrom-json).role.arn
aws iam attach-role-policy --role-name $rolename --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole
aws lambda create-function --function-name $func --zip-file fileb://$PSScriptRoot/../../../lambdas/admin/function.zip --handler index.handler `
--runtime java21 --role $arn