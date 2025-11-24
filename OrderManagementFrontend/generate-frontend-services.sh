# Default values
jdk_bin_path="C:\Users\User\.jdks\openjdk-23.0.2\bin"
url="http://localhost:8092/ordermanagement/api/v3/api-docs.yaml"
filename="openapi.yaml"

# Fetch the content from the URL and save it to the file
# Using curl to download in UTF-8
curl -sSL "$url" -o "$filename"

# Check if java is available
if ! command -v java >/dev/null 2>&1; then
  # If no jdk_bin_path is provided, use the default
  if [ -z "$jdk_bin_path" ]; then
    export PATH="$PATH:$HOME/.jdks/corretto-17.0.5/bin"
  else
    export PATH="$PATH:$jdk_bin_path"
  fi
fi

# Removing all previous generated files to avoid having old files
# Uncomment the following line if you want to remove the directory content
rm -rf src/app/generated/*

# Generate the API client using openapi-generator-cli
openapi-generator-cli generate -g typescript-angular -i "$filename" \
  --skip-validate-spec \
  --additional-properties=useSingleRequestParameter=true \
  --additional-properties=supportsES6=true \
  -o src/app/generated/ordermanagement
