#!/bin/sh

pids=`ps -C ssh -o pid,args | grep "judge.judgehost[0-9]\+.log" | sed 's/ .*//'`;
for pid in $pids
do
  kill $pid
done

