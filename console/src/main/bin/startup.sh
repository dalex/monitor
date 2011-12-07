#!/bin/sh

MAINCLASS=ru.bigbuzzy.monitor.RunMonitor

# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  echo "ls $ls"
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

# Get standard environment variables
PRGDIR=`dirname "$PRG"`

CLIENT_HOME=`cd "$PRGDIR/.." ; pwd`

CLASSPATH="$CLIENT_HOME/conf:$CLIENT_HOME/lib/*"

if [ ! -z "$1" ] && [ ! -z "$2" ] ; then
    java -classpath $CLASSPATH $MAINCLASS $1 $2
elif [ ! -z "$1" ] && [ -z "$2" ] ; then
    java -classpath $CLASSPATH $MAINCLASS $1 $CLIENT_HOME/conf/console.properties
else
    java -classpath $CLASSPATH $MAINCLASS $CLIENT_HOME/conf/config.xml $CLIENT_HOME/conf/console.properties
fi
