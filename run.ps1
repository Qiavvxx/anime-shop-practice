$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot
New-Item -ItemType Directory -Force -Path "out" | Out-Null
$javaSources = Get-ChildItem -Path "src/main/java" -Recurse -Filter "*.java" | ForEach-Object { $_.FullName }
if (-not $javaSources) {
    throw "Java source files were not found."
}
javac -encoding UTF-8 -d out $javaSources
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}
java -cp out ru.mirea.animeshop.Main
