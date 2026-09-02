if ($args.Count -eq 0) {
    Write-Host "Usage: .\convert.ps1 /<your file path>"
    exit 1
}

$FilePath = $args[0]

if (-not (Test-Path $FilePath)) {
    Write-Error "Couldn't find the parameter file '$FilePath'."
    exit 1
}

$lineNumber = 0
Get-Content $FilePath | ForEach-Object {
    $line = $_.Trim()
    $lineNumber++
    
    if ([string]::IsNullOrEmpty($line) -or $line.StartsWith('#')) {
        return
    }

    $parts = $line -split ',' | ForEach-Object { $_.Trim() }
    Write-Host "`n --- Line $lineNumber ---"

    if ($parts.Count -lt 3) {
        Write-Warning "Not enough parameters, skipping."
        return
    }

    $SourceBase = $parts[0]
    $TargetBase = $parts[1]
    $NumbersToConvert = $parts[2..($parts.Count - 1)]

    $isValidLine = $true
    foreach ($number in $NumbersToConvert) {
        try {
            $decimalValue = [System.Convert]::ToInt64($number, $SourceBase)
            $convertedValue = [System.Convert]::ToString($decimalValue, $TargetBase).ToUpper()
            Write-Host "$number -> $convertedValue"
        }
        catch {
            Write-Error "Number '$number' is invalid for base $SourceBase."
            $isValidLine = $false
            break
        }
    }
    
    if (-not $isValidLine) {
        Write-Host "Skipping remaining conversions for this line."
    }
}