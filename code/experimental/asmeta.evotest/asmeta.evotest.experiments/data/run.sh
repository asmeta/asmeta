#!/usr/bin/env bash

JAR="experiments-runner.jar"

case "$1" in
  generation-and-analysis)
    java -cp "$JAR" asmeta.evotest.experiments.ScenarioGeneratorRunner > generation.log 2>&1
    generation_status=$?

    if [ "$generation_status" -ne 0 ]; then
      exit "$generation_status"
    fi

    # Read the analysis directory from the same configuration used by generation.
    results_dir=$(sed -n 's/^[[:space:]]*target_folder:[[:space:]]*"\([^"]*\)".*/\1/p' generation_config.yaml | head -n 1)
    if [ -z "$results_dir" ]; then
      echo "Unable to determine target_folder from generation_config.yaml" >&2
      exit 1
    fi

    if [ "$2" == "--shuffle" ]; then
      java -cp "$JAR" asmeta.evotest.experiments.AnalysisRunner "$results_dir" --shuffle > analysis.log 2>&1
    elif [ -n "$2" ]; then
      echo "Error: the optional argument must be --shuffle" >&2
      exit 1
    else
      java -cp "$JAR" asmeta.evotest.experiments.AnalysisRunner "$results_dir" > analysis.log 2>&1
    fi
    exit $?
    ;;

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
    echo -e "*for generating and analyzing the suites configured in 'generation_config.yaml':\n\t./run.sh generation-and-analysis [--shuffle]"
    echo -e "*for generating using the config file 'generation_config.yaml':\n\t./run.sh generation"
    echo -e "*for generating using random with number of tests and steps retrived from suites generated with 'generation' option:\n\t./run.sh rnd-generation <path/to/results> <path/to/asm_examples> <path/to/model_list.txt> [--shuffle]"
    echo -e "*for generating using iterative random generation:\n\t./run.sh iterative-rnd-generation <path/to/results> <path/to/asm_examples> <path/to/model_list.txt> <timeBudget> [--shuffle]"
    echo -e "*for running the analysis\n\t./run.sh analysis <path/to/results> [--shuffle]"
    ;;
esac
