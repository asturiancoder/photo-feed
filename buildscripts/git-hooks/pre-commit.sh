#!/bin/sh


CHANGED_KOTLIN_FILES="$(git --no-pager diff --name-status --no-color --cached | awk '$1 != "D" && $2 ~ /\.kts|\.kt/ { print $2}')"

if [ -z "$CHANGED_KOTLIN_FILES" ]; then
    echo "No Kotlin staged files."
    exit 0
fi;

echo "Running static analysis over these files:"
echo "$CHANGED_KOTLIN_FILES"

##################
# Spotless
##################

echo "Running Spotless..."
./gradlew spotlessCheck
RESULT=$?

if [ $RESULT -ne 0 ]; then
  echo "Spotless found format violations, so aborting the commit..."

  ./gradlew --quiet spotlessApply

  echo "Recommended changes from spotless have been applied."
  exit $RESULT
fi

exit $RESULT