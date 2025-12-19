#!/usr/bin/env bash

JAR="experiments-runner.jar"

case "$1" in
  generation)
    java -cp "$JAR" asmeta.evotest.experiments.ScenarioGeneratorRunner > generation.log 2>&1
    ;;

  rnd-generation)
    if [ "$5" == "--shuffle" ]; then
      java -cp "$JAR" asmeta.evotest.experiments.RandomGeneratorRunner "$2" "$3" "$4" --shuffle > rnd_generation.log 2>&1
    else
      java -cp "$JAR" asmeta.evotest.experiments.RandomGeneratorRunner "$2" "$3" "$4" > rnd_generation.log 2>&1
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
    echo -e "*for generating using the config file 'generation_config.yaml':\n\t./run.sh generation"
    echo -e "*for generating using random number of tests and steps retrived from suites generated with 'generation' option:\n\t./run.sh rnd-generation <path/to/results> <path/to/asm_examples> <path/to/model_list.txt> [--shuffle]"
    echo -e "*for running the analysis\n\t./run.sh analysis <path/to/results> [--shuffle]"
    ;;
esac