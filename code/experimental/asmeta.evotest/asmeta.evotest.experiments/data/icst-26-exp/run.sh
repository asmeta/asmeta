#!/usr/bin/env bash

JAR="icst-experiments-runner.jar"

case "$1" in
  scenario)
    java -cp "$JAR" asmeta.evotest.experiments.ScenarioGeneratorRunner
    ;;

  coverage)
    if [[ "$3" == "--shuffle" ]]; then
      java -cp "$JAR" asmeta.evotest.experiments.CoverageAnalysisRunner "$2" --shuffle
    else
      java -cp "$JAR" asmeta.evotest.experiments.CoverageAnalysisRunner "$2"
    fi
    ;;

  *)
    echo "Usage:"
    echo "  ./run.sh scenario"
    echo "  ./run.sh coverage <path> [--shuffle]"
    ;;
esac