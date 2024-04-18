[void] [System.Reflection.Assembly]::LoadWithPartialName("System.Windows.Forms")
[void] [System.Reflection.Assembly]::LoadWithPartialName("System.Drawing")

$form = New-Object System.Windows.Forms.Form
$location = 0

Get-ChildItem -Directory -Filter "*-service" | ForEach-Object {
    $button = New-Object System.Windows.Forms.Button
    $button.Size = New-Object System.Drawing.Size(120, 30)
    $button.Text = $_
    $button.Location = "0, " + $global:location * 30
    $global:location++
    $button.Add_Click({Start-Process powershell -ArgumentList ("-Command cd " + $this.text + "; & ./mvnw 'package' '-Dmaven.test.skip'; sleep 3")})
    $form.Controls.Add($button)
}

$close = New-Object System.Windows.Forms.Button
$close.Location = "0, 150"
$close.Size = New-Object System.Drawing.Size(90, 30)
$close.Text = "Close window"
$close.Add_Click({$form.Close()})

$form.Controls.Add($close)
$form.ShowDialog((New-Object System.Windows.Forms.Form -Property @{TopMost = $true}))