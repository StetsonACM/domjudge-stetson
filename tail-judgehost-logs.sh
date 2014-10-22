#!/bin/sh

for j in `vagrant status | grep -oE '(judgehost[0-9]+)'`
do
  vagrant ssh $j -- "sudo tail -f /opt/domjudge/judgehost/log/judge.$j.log" >> judgehosts.log &
done

