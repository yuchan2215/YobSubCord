#!/bin/bash
cd `dirname $0`
/usr/bin/java -Djava.util.concurrent.ForkJoinPool.common.parallelism=1 -jar $1