# kafka-demo
kafka-demo project demonstrates spring boot kafka project with both producer and consumer retry mechanism

--
commands for project setup:

start Zookeeper:
zookeeper-server-start.bat C:\kafka_2.12-2.4.1\config\zookeeper.properties

start broker:
kafka-server-start.bat C:\kafka_2.12-2.4.1\config\server.properties (default broker.id=0, no need to uncomment listeners=PLAINTEXT://:9092 and update the log.dirs=./tmp/kafka-logs-0)

To start multiple brokers:
kafka-server-start.bat C:\kafka_2.12-2.4.1\config\server2.properties (update broker.id=1, uncomment listeners=PLAINTEXT://:9093 and update the log.dirs=./tmp/kafka-logs-1)
kafka-server-start.bat C:\kafka_2.12-2.4.1\config\server3.properties (update broker.id=2, uncomment listeners=PLAINTEXT://:9094 and update the log.dirs=./tmp/kafka-logs-2)

kafka- topic creation syntax with out in-sync replication factor:
kafka-topics --zookeeper 127.0.0.1:2181 --topic 'TOPIC_NAME' --create --partitions 3 --replication-factor 1

topics needed to run this codebase:
kafka-topics --zookeeper 127.0.0.1:2181 --topic kafka-sample --create --partitions 3 --replication-factor 1
kafka-topics --zookeeper 127.0.0.1:2181 --topic kafka-sample_delay1 --create --partitions 3 --replication-factor 1
kafka-topics --zookeeper 127.0.0.1:2181 --topic kafka-sample_delay2 --create --partitions 3 --replication-factor 1
kafka-topics --zookeeper 127.0.0.1:2181 --topic kafka-sample_final --create --partitions 3 --replication-factor 1
kafka-topics --zookeeper 127.0.0.1:2181 --topic kafka-sample_nonRecoverable --create --partitions 3 --replication-factor 1


with min.insync.replication-factor for producer retry:
kafka-topics --zookeeper 127.0.0.1:2181 --topic 'TOPIC_NAME' --create --partitions 3 --replication-factor 3 --config min.insync.replicas=2

--

my reference:

List out of all the topics:
kafka-topics --zookeeper 127.0.0.1:2181 --list
Topic description:
kafka-topics --zookeeper 127.0.0.1:2181 --topic 'TOPIC_NAME' --describe

Kafka Console producer command line:
kafka-console-producer (for documentation of command)

Producing the message from kafka-console-producer:
kafka-console-producer --broker-list 127.0.0.1:9092 --topic 'TOPIC_NAME'
>message1

Producing with message acked:
kafka-console-producer --broker-list 127.0.0.1:9092 --topic 'TOPIC_NAME' --producer-property acks=all

Console Consumer:
kafka-console-consumer --bootstrap
kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic 'TOPIC_NAME'

Kafka Consumer groups:
kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic 'TOPIC_NAME' --group 'GROUP_NAME'
Group to consume messages from beginning:
kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic 'TOPIC_NAME' --from-beginning
kafka-consumer-groups --bootstrap-server localhost:9092 --list (give list of all groups)
kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group 'GROUP_NAME' (Desc group)

shift offset to -2 on 3 partition will give 6 messages:
kafka-consumer-groups --bootstrap-server localhost:9092 --group 'GROUP_NAME' --rest-offsets  --shift-by -2 --execute --topic 'TOPIC_NAME'

