# --- 1. Basic Input Check ---

# Check if the user gave exactly one file path.
if ($args.Count -ne 1) {
    Write-Host "Usage: .\convert.ps1 MyConversions.txt"
    exit 1
}

$FilePath = $args[0]
$LineCounter = 0

# Check if the file is actually there.
if (-not (Test-Path $FilePath)) {
    Write-Host "Error: Cannot find the file at $FilePath"
    exit 1
}

Write-Host "Starting conversion for file: $FilePath"

# --- 2. Process File Line by Line ---

# Read all the lines from the file.
$AllLines = Get-Content $FilePath

foreach ($Line in $AllLines) {
    $LineCounter = $LineCounter + 1

    # Remove extra spaces from the ends.
    $TrimmedLine = $Line.Trim()

    # Skip if the line is blank or starts with a comment (#).
    if (($TrimmedLine.Length -eq 0) -or $TrimmedLine.StartsWith('#')) {
        continue # Skip to the next line.
    }

    # Split the line by the comma, getting all the parts.
    # We use .Trim() on each part to clean up spaces around commas.
    $Parts = $TrimmedLine -split ',' | ForEach-Object { $_.Trim() }

    # Check if we have at least 3 things: Source Base, Target Base, and at least one Number.
    if ($Parts.Count -lt 3) {
        Write-Host "Warning on line $LineCounter: Not enough commas! Skipping."
        continue
    }

    # --- 3. Simple Base Extraction ---

    # Get the bases from the first two spots.
    $SourceBaseStr = $Parts[0]
    $TargetBaseStr = $Parts[1]

    # Convert the base strings to actual numbers (integers).
    # We use simple casting and hope the user provided good numbers!
    [int]$SourceBase = 0
    [int]$TargetBase = 0

    # Basic base validation (this is where a beginner might put a simple check)
    try {
        $SourceBase = [int]$SourceBaseStr
        $TargetBase = [int]$TargetBaseStr
    }
    catch {
        Write-Host "Error on line $LineCounter: The bases must be numbers. Skipping line."
        continue
    }

    if (($SourceBase -lt 2 -or $SourceBase -gt 36) -or ($TargetBase -lt 2 -or $TargetBase -gt 36)) {
        Write-Host "Error on line $LineCounter: Bases must be between 2 and 36. Skipping line."
        continue
    }

    Write-Host "--- Line $LineCounter: Converting from Base $SourceBase to Base $TargetBase ---"

    # --- 4. Process Numbers and Conversion ---

    # We need to process all parts starting from the 3rd element (index 2).
    # We use a simple loop index for this.
    $Index = 2
    $ConversionSuccessful = $true

    while ($Index -lt $Parts.Count) {
        $OriginalNumber = $Parts[$Index]

        # Check for empty number strings
        if ([string]::IsNullOrWhiteSpace($OriginalNumber)) {
            Write-Host "Skipping empty number."
            $Index++
            continue
        }

        # The core conversion. We use a basic error check instead of a proper try/catch
        # to simulate a beginner's approach.
        try {
            # Step 1: Convert the number string (in $SourceBase) to a big integer.
            $DecimalValue = [System.Convert]::ToInt64($OriginalNumber, $SourceBase)

            # Step 2: Convert the big integer to a string in $TargetBase.
            $ConvertedNumber = [System.Convert]::ToString($DecimalValue, $TargetBase).ToUpper()

            # Print the result.
            Write-Host "$OriginalNumber -> $ConvertedNumber"
        }
        catch {
            # Simple error handling for bad numbers (like 'Z' in Base 10).
            Write-Host "!! Error converting '$OriginalNumber' on this line. Stopping here for line $LineCounter."
            $ConversionSuccessful = $false
            break # Stop the inner while loop immediately, as requested.
        }

        $Index++ # Move to the next number on the line.
    }
}

Write-Host "Done with all the lines!"