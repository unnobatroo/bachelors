param(
    [string]$filePath
)

Get-Content $filePath | ForEach-Object{$_.Substring(1, 4)}