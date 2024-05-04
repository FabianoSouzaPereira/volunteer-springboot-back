#!/bin/bash

# Caminho para o script do Zookeeper
ZOOKEEPER_SCRIPT="/Users/fabiano/Documents/kafka/bin/zookeeper-server-start.sh"

# Caminho para o script do Kafka
KAFKA_SCRIPT="/Users/fabiano/Documents/kafka/bin/kafka-server-start.sh"

# Verificar se o Zookeeper está em execução
if pgrep -x "java" | grep -q "zookeeper"; then
    echo "O Zookeeper já está em execução."
else
    # Iniciar o Zookeeper
    $ZOOKEEPER_SCRIPT /Users/fabiano/Documents/kafka/config/zookeeper.properties &
fi

# Aguardar até que o Zookeeper esteja pronto
while ! nc -z localhost 2181; do
    sleep 8 # Esperar por 8 segundos antes de verificar novamente
done

# Verificar se o Kafka está em execução
if pgrep -x "java" | grep -q "kafka"; then
    echo "O Kafka já está em execução."
else
    # Iniciar o Kafka após o Zookeeper estar pronto
    $KAFKA_SCRIPT /Users/fabiano/Documents/kafka/config/server.properties &
fi