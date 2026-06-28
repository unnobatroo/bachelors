$PipelineInput = @($Input)

$WordsCount = 0

if ($PipelineInput.Count -gt 0) {
    foreach ($Item in $PipelineInput) {
        $WordsCount += ($Item -split '\s+' | Measure-Object).Count
    }
}

$WordsCount += $args.Count

Write-Output $WordsCount