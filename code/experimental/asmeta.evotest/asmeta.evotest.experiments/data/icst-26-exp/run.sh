#!/usr/bin/env bash

JAR="icst-experiments-runner.jar"

case "$1" in
  all)
    java -cp "$JAR" asmeta.evotest.experiments.ScenarioGeneratorRunner > generation.log 2>&1
    java -cp "$JAR" asmeta.evotest.experiments.AnalysisRunner ./results > analysis.log 2>&1
    ;;

  generation)
    java -cp "$JAR" asmeta.evotest.experiments.ScenarioGeneratorRunner > generation.log 2>&1
    ;;

  analysis)
    if [ "$3" == "--shuffle" ]; then
      java -cp "$JAR" asmeta.evotest.experiments.AnalysisRunner "$2" --shuffle > analysis.log 2>&1
    else
      java -cp "$JAR" asmeta.evotest.experiments.AnalysisRunner "$2" > analysis.log 2>&1
    fi
    ;;

  *)
    echo "Usage:"
    echo "  ./run.sh all"
    echo "  ./run.sh generation"
    echo "  ./run.sh analysis<path> [--shuffle]"
    ;;
esac