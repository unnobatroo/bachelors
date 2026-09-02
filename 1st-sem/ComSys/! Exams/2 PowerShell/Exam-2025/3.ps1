$fileName = if ($args.Count -gt 0) { $args[0] } else { '' }

while (-not (Test-Path $fileName -PathType Leaf)) {
    Write-Host ""
    $fileName = Read-Host "Please enter the filename"
}

Write-Host ""

Select-String -Path $fileName -Pattern '\b[Aa]\w*\b' | ForEach-Object {
    $_.Line
}

Write-Host ""