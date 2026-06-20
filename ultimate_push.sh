#!/bin/bash
echo "🔥 ULTIMATE FORCE PUSH"

cd /workspaces/Secret-ai

# Buat branch baru
git checkout -b trigger-build

# Buat perubahan kecil
echo "" >> README.md
git add README.md
git commit -m "Trigger build"

# Push branch
git push -f origin trigger-build

# Merge ke main
git checkout main
git merge trigger-build
git push -f origin main

# Hapus branch
git branch -d trigger-build
git push origin --delete trigger-build

echo ""
echo "✅ Build triggered!"
echo "📱 Cek: https://github.com/mrrvpa-gemini/Secret-ai/actions"
