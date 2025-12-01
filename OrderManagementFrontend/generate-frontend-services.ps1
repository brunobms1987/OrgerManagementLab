[CmdletBinding()]
param (
    [Parameter(HelpMessage="The jdk directory.")]
    [string] $jdk_bin_path,
    [Parameter(HelpMessage="The url of the springDoc.")]
    [string] $url = "http://localhost:8092/ordermanagement/api/v3/api-docs.yaml",
    [Parameter(HelpMessage="The target file name.")]
    [string] $filename = "openapi.yaml"
)

# Generate yaml file from WebApi
curl $url -o $filename

$content = (Invoke-WebRequest $url).Content
[System.Text.Encoding]::UTF8.GetString($content, 0, $content.Length) | Out-File -encoding utf8 $filename

try {
  java --version | Out-Null
} catch {
  if ("" -eq $jdk_bin_path) {
    $env:PATH += ";$HOME\.jdks\corretto-17.0.5\bin"
  } else {
    $env:PATH += ";$jdk_bin_path"
  }
}

# Removing all previous files to avoid having old files
Remove-Item -Recurse src/app/generated/ordermanagement/*

openapi-generator-cli generate -g typescript-angular -i $filename `
  --skip-validate-spec --additional-properties=useSingleRequestParameter=true `
  --additional-properties=supportsES6=true -o src/app/generated/ordermanagement

# Removing yaml file generated
Remove-Item $filename

