#!/bin/sh
IP=$1
HOSTS=`grep -v domserver /etc/hosts`
echo -e "$HOSTS" > /etc/hosts
echo "$IP domserver" >> /etc/hosts

