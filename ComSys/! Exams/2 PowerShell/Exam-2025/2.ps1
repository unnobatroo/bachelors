foreach ($char in $args) {
    $number = [int]::Parse($char)
    $prime = $true
    
    if ($number -le 1) {
        $prime = $false
    }
    elseif ($number -eq 2) {
        # already "$prime = $true"
    }
    elseif ($number % 2 -eq 0) {
        $prime = $false
    }
    else {
        $limit = [System.Math]::Sqrt($number)
        
        for ($i = 3; $i -le $limit; $i += 2) {
            if ($number % $i -eq 0) {
                $prime = $false
                break
            }
        }
    }

    if ($prime) {
        Write-Output $number
    }
}