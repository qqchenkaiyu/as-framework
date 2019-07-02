#!/bin/sh

export LANG="zh_CN.UTF-8"
v_starttime=$(date +%Y%m%d%H%M%S)

mmsid=`ps -ef|grep "ccwbs" |grep -v grep|awk '{print $2 }'`
                        if [ -z "$mmsid" ];then
				sleep 1

                        else
                               ps -ef | grep hwwbs | grep -v grep | awk '{print $2}' | xargs kill
				sleep 2

                        fi

nohup java -jar  -server -Xms1g -Xmx1g  -XX:PermSize=128m -XX:MaxPermSize=256m -Xloggc:./proglog/gc$v_starttime.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Diname=hwwbs  hw-bs-0.0.1-SNAPSHOT.jar > /dev/null 2> error.log &
