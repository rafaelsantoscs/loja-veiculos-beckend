# Script para recompilar e reiniciar o backend Spring Boot

Write-Host "====================================" -ForegroundColor Cyan
Write-Host "Recompilando Backend Spring Boot" -ForegroundColor Cyan
Write-Host "====================================" -ForegroundColor Cyan

# Navegar para o diretório do backend
Set-Location "c:\Users\Rafael-CDS\Desktop\Nova pasta (3)\suporte-back"

Write-Host "`n[1/3] Limpando build anterior..." -ForegroundColor Yellow
.\mvnw.cmd clean

Write-Host "`n[2/3] Compilando código..." -ForegroundColor Yellow
.\mvnw.cmd compile

Write-Host "`n[3/3] Reiniciando aplicação..." -ForegroundColor Yellow
Write-Host "IMPORTANTE: Pare o backend anterior (Ctrl+C) e depois execute:" -ForegroundColor Red
Write-Host ".\mvnw.cmd spring-boot:run" -ForegroundColor Green

Write-Host "`n====================================" -ForegroundColor Cyan
Write-Host "Recompilação concluída!" -ForegroundColor Green
Write-Host "====================================" -ForegroundColor Cyan
