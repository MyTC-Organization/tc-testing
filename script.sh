for i in $(seq 1 3); do echo "" >> gradle.properties; git add gradle.properties ; git commit -m "chore: $(git rev-parse --abbrev-ref HEAD) test"; done

git push ; clear

