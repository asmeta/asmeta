#!/usr/bin/env bash

JAR="icst-experiments-runner.jar"

case "$1" in
  all)
	java -cp "$JAR" asmeta.evotest.experiments.ScenarioGeneratorRunner > generation.log
	java -cp "$JAR" asmeta.evotest.experiments.CoverageAnalysisRunner ./results > analysis.log
	;;

  scenario)
    java -cp "$JAR" asmeta.evotest.experiments.ScenarioGeneratorRunner > generation.log
    ;;

  coverage)
    if [ "$3" == "--shuffle" ]; then
      java -cp "$JAR" asmeta.evotest.experiments.CoverageAnalysisRunner "$2" --shuffle > analysis.log
    else
      java -cp "$JAR" asmeta.evotest.experiments.CoverageAnalysisRunner "$2" > analysis.log
    fi
    ;;

  *)
    echo "Usage:"
    echo "  ./run.sh all"
    echo "  ./run.sh scenario"
    echo "  ./run.sh coverage <path> [--shuffle]"
    ;;
esac