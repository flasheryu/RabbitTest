#!/bin/sh
for((i=0; i<100000000; i++))
do
rabbitmqctl list_queues >> list_queues.out 
sleep 0.01
done
