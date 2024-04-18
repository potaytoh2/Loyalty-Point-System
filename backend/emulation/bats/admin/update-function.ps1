$func=Get-Content $PSScriptRoot/functionname.txt
aws lambda update-function-code --function-name $func --zip-file fileb://$PSScriptRoot/../../../lambdas/function.zip