#!/usr/bin/env bash

JAR="experiments-runner.jar"

case "$1" in
  generation)
    EVOSUITE_VERSION="${2:-1.2.0}"
    java -cp "$JAR" asmeta.evotest.experiments.ScenarioGeneratorRunner "$EVOSUITE_VERSION" "${3:-}" > generation.log 2>&1
    ;;

  rnd-generation)
    java -cp "$JAR" asmeta.evotest.experiments.RandomGeneratorRunner "$2" "$3" "$4" "${@:5}" > rnd_generation.log 2>&1
    ;;
    
  iterative-rnd-generation)
    if [ "$6" == "--shuffle" ]; then
      java -cp "$JAR" asmeta.evotest.experiments.IterativeRandomGeneratorRunner "$2" "$3" "$4" "$5" --shuffle > iterative_rnd_generation.log 2>&1
    else
      java -cp "$JAR" asmeta.evotest.experiments.IterativeRandomGeneratorRunner "$2" "$3" "$4" "$5" > iterative_rnd_generation.log 2>&1
    fi
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
    echo -e "*for generating using the config file 'generation_config.yaml':\n\t./run.sh generation [1.0.6|1.2.0] [algorithm]\n\tExample: ./run.sh generation 1.2.0 MONOTONIC_GA"
    echo -e "*for generating using random with number of tests and steps retrieved from suites generated with 'generation' option:\n\t./run.sh rnd-generation <path/to/results> <path/to/asm_examples> <path/to/model_list.txt> [--shuffle] [--folders folder1,folder2,...], if --folders is not specified, default to atgttests,evoavallatests"
    echo -e "*for generating using iterative random generation:\n\t./run.sh iterative-rnd-generation <path/to/results> <path/to/asm_examples> <path/to/model_list.txt> <timeBudget> [--shuffle]"
    echo -e "*for running the analysis\n\t./run.sh analysis <path/to/results> [--shuffle]"
    ;;
esac
