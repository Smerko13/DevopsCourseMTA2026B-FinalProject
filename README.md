switch the script to:
$apiKey = $env:UPTIME_API_KEY

$response = Invoke-RestMethod `
    -Uri "https://api.uptimerobot.com/v2/getMonitors" `
    -Method Post `
    -ContentType "application/x-www-form-urlencoded" `
    -Body @{
        api_key = $apiKey
        format = "json"
    }

$monitor = $response.monitors[0]

Write-Host "Monitor Name: $($monitor.friendly_name)"
Write-Host "URL: $($monitor.url)"
Write-Host "Status: $($monitor.status)"

if ($monitor.status -eq 2) {
    Write-Host "Application is UP"
    exit 0
}
else {
    Write-Host "Application is DOWN"
    exit 1
}
